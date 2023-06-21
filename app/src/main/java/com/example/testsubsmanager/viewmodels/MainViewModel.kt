package com.example.testsubsmanager.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.example.testsubsmanager.database.AppDatabaseRepository
import com.example.testsubsmanager.database.dto.Currency
import com.example.testsubsmanager.database.dto.Subscription
import com.example.testsubsmanager.database.dto.TypeDuration
import com.example.testsubsmanager.services.currency.CurrencyRetrofitClient
import com.example.testsubsmanager.ui.models.FormData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import javax.inject.Inject
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt


class MainViewModel @Inject constructor(private val repository: AppDatabaseRepository) : ViewModel() {

    private lateinit var displayedCurrency: Currency
    private var selectedCurrency: Currency? = null
    private var selectedSubscription: Subscription? = null
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val mainScope = CoroutineScope(Dispatchers.Main)
    var formData: MutableLiveData<FormData> = MutableLiveData()
    suspend fun setDisplayedCurrency(code: String){
        return withContext(Dispatchers.IO) {
            val currency = repository.getCurrencyByCode(code)
            displayedCurrency = currency
        }
    }

    suspend fun getAllSubscriptions(): List<Subscription> {
        return withContext(Dispatchers.IO) {
            val subscriptions = repository.getAllSubscriptions()
            subscriptions
        }
    }

    suspend fun getAllCurrencies(): List<Currency> {
        return withContext(Dispatchers.IO) {
            val currencies = repository.getAllCurrencies()
            currencies
        }
    }

    fun saveSubscription(subscriptionName: String,
                         color: String = "#FFFFFF",
                         price: Double = 0.0,
                         startDate: String = LocalDate.now().toString(),
                         duration: Int = 1,
                         typeDuration: String = "Months") {
        val subCurrency: Currency = selectedCurrency!!
        val formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu", Locale.ENGLISH)
        val subStartDate: LocalDate = LocalDate.parse(startDate, formatter)
        val renewalDate: LocalDate = calculateRenewalDate(subStartDate, duration, typeDuration.uppercase())

        val subscription = Subscription(
            nameSub = subscriptionName,
            color = color,
            price = price,
            currency = subCurrency,
            startDate = subStartDate,
            renewalDate = renewalDate,
            duration = duration,
            typeDuration = TypeDuration.valueOf(typeDuration.uppercase())
        )

        ioScope.launch {
            repository.insertSubscription(subscription)
        }
    }

    private fun calculateRenewalDate(startDate: LocalDate, duration: Int, typeDuration: String): LocalDate {
        var renewalDate: LocalDate = startDate

        when (typeDuration) {
            "DAYS" -> renewalDate = startDate.plusDays(duration.toLong())
            "WEEKS" -> renewalDate = startDate.plusWeeks(duration.toLong())
            "MONTHS" -> renewalDate = startDate.plusMonths(duration.toLong())
            "YEARS" -> renewalDate = startDate.plusYears(duration.toLong())
        }

        return renewalDate
    }


    private suspend fun getCurrencyByCode(code: String): Currency{
        return withContext(Dispatchers.IO) {
            val currency = repository.getCurrencyByCode(code)
            currency
        }
    }

    suspend fun fetchAndSaveCurrencyRates(date: String) {
        try {
            val rubCurrency = try {
                getCurrencyByCode("RUB")
            } catch (_: Exception) {
                val rubCurrency = Currency(
                    code = "RUB",
                    name = "Russian Ruble",
                    exchangeRate = 1.0,
                    quoteDate = "16/11/1988"
                )
                runBlocking {
                    ioScope.launch {
                        repository.insertCurrency(rubCurrency)
                        setDisplayedCurrency("RUB")
                    }
                }
                rubCurrency
            }

            if (rubCurrency.quoteDate != date) {
                val valCurs = CurrencyRetrofitClient.getApi().getCurrencyRates(date)

                if (valCurs.isSuccessful) {
                    val currenciesCBRs = valCurs.body()!!.valutes
                    val listCurrencies = getAllCurrencies()

                    currenciesCBRs.forEach { currencyCBR ->
                        val exchangeRate = currencyCBR.value.replace(",", ".").toDouble()
                        val adjustedExchangeRate = exchangeRate / currencyCBR.nominal.toDouble()
                        val existingCurrency = listCurrencies.find { it.code == currencyCBR.charCode }

                        if (existingCurrency != null) {
                            val updatedCurrency = existingCurrency.copy(
                                exchangeRate = adjustedExchangeRate,
                                quoteDate = date
                            )
                            ioScope.launch {
                                repository.updateCurrency(updatedCurrency)
                            }
                        } else {
                            val newCurrency = Currency(
                                code = currencyCBR.charCode,
                                name = currencyCBR.name,
                                exchangeRate = adjustedExchangeRate,
                                quoteDate = date
                            )
                            ioScope.launch {
                                mainScope.launch {
                                    repository.insertCurrency(newCurrency)
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("SB", e.toString())
        }
    }


    fun setSelectedCurrency(currency: Currency) {
        selectedCurrency = currency
    }

    fun getSelectedCurrency(): Currency? {
        return selectedCurrency
    }

    fun getPrettyTotalMonthlyCost(subscriptions: List<Subscription>): String {
        val totalCostResult = calculateTotalMonthlyCost(subscriptions)
        val totalCost = totalCostResult.first
        val isConverted = totalCostResult.second

        var prettyTotalCost = if (totalCost != 0.0) {formatNumber(totalCost)} else 0

        if (isConverted) {
            prettyTotalCost = "≈$prettyTotalCost"
        }

        return "$prettyTotalCost ${displayedCurrency.code}"
    }

    private fun formatNumber(number: Double): String {
        val suffixes = listOf("", "K", "M", "B", "T")
        val numValue = floor(log10(number)).toInt()
        val numIndex = (numValue / 3)

        if (numIndex < 2) {
            return number.toFixed(2).toString()
        }

        var formattedNumber = number / 10.0.pow((numIndex * 3).toDouble())
        formattedNumber = if (formattedNumber % 1 != 0.0) {
            formattedNumber.toFixed(2)
        } else {
            formattedNumber.toInt().toDouble() // Convert to Int if no decimal places
        }

        return "$formattedNumber ${suffixes[numIndex]}"
    }

    private fun Double.toFixed(decimalPlaces: Int): Double {
        val factor = 10.0.pow(decimalPlaces.toDouble())
        return (this * factor).roundToInt() / factor
    }

    private fun calculateTotalMonthlyCost(subscriptions: List<Subscription>): Pair<Double, Boolean> {
        val displayedCurrency: Currency = displayedCurrency
        var totalCost = 0.0
        var countConversions = 0
        var isConverted = false
        var exchangeRate = 1.0
        if (subscriptions.isNotEmpty()){
            for (subscription in subscriptions) {
                if (subscription.currency != displayedCurrency) {
                    exchangeRate = convertCurrency(subscription.currency, displayedCurrency)
                    countConversions += 1
                } else {
                    exchangeRate = subscription.currency.exchangeRate
                }

                val subscriptionCostInDisplayCurrency = calculateSubscriptionMonthlyCostInDisplayCurrency(subscription, exchangeRate)
                totalCost += subscriptionCostInDisplayCurrency
            }
            isConverted = (countConversions > 0)
        }
        return Pair(totalCost, isConverted)
    }

    private fun convertCurrency(sourceCurrency: Currency, targetCurrency: Currency): Double {
        return sourceCurrency.exchangeRate / targetCurrency.exchangeRate
    }

    private fun calculateSubscriptionMonthlyCostInDisplayCurrency(subscription: Subscription, exchangeRate: Double): Double {
        val subscriptionCost = calculateSubscriptionMonthlyCost(subscription)
        return subscriptionCost * exchangeRate
    }

    private fun calculateSubscriptionMonthlyCost(subscription: Subscription): Double {
        val durationInMonths = convertDurationToMonths(subscription.duration, subscription.typeDuration, subscription.startDate, subscription.renewalDate)
        return subscription.price / durationInMonths
    }

    private fun convertDurationToMonths(duration: Int, typeDuration: TypeDuration, startDate: LocalDate, renewalDate: LocalDate): Double {
        val daysInPeriod = ChronoUnit.DAYS.between(startDate, renewalDate)
        val monthsInPeriod = ChronoUnit.MONTHS.between(startDate, renewalDate)
        val remainingDays = daysInPeriod - (monthsInPeriod * 30) // Assuming 30 days in a month

        var totalMonths = monthsInPeriod.toDouble()
        totalMonths += remainingDays.toDouble() / 30 // Adjust for the remaining days

        return when (typeDuration) {
            TypeDuration.DAYS -> totalMonths * (duration.toDouble() / 30)
            TypeDuration.WEEKS -> totalMonths * (duration.toDouble() / 4)
            TypeDuration.MONTHS -> totalMonths * duration.toDouble()
            TypeDuration.YEARS -> totalMonths * (duration.toDouble() * 12)
        }
    }

    fun settingsChanged(key: String?, context: Context) {
        if (key != null) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

            when (key) {
                "notifications" -> {
                    // Handle the change in the "notifications" setting
                }
                "theme" -> {
                    // Handle the change in the "theme" setting
                }
                "currency" -> {
                    val value = sharedPreferences.getString(key, "RUB")
                    runBlocking{ value?.let { setDisplayedCurrency(it) } }
                }
            }
        }
    }

    fun setSelectedSubscription(subscription: Subscription?) {
        selectedSubscription = subscription
    }

    fun getSelectedSubscription(): Subscription? {
        return selectedSubscription
    }

    fun updateSubscription(subscriptionName: String, color: String, price: Double, startDate: String, duration: Int, typeDuration: String) {
        val subCurrency: Currency = selectedCurrency!!
        val formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu", Locale.ENGLISH)
        val subStartDate: LocalDate = LocalDate.parse(startDate, formatter)
        val renewalDate: LocalDate = calculateRenewalDate(subStartDate, duration, typeDuration.uppercase())

        val updatedSubscription = Subscription(
            id = selectedSubscription!!.id,
            nameSub = subscriptionName,
            color = color,
            price = price,
            currency = subCurrency,
            startDate = subStartDate,
            renewalDate = renewalDate,
            duration = duration,
            typeDuration = TypeDuration.valueOf(typeDuration.uppercase()))

        ioScope.launch {
            mainScope.launch {
                repository.updateSubscription(updatedSubscription)
            }
        }

    }

}
