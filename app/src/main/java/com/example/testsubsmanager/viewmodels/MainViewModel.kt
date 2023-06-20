package com.example.testsubsmanager.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testsubsmanager.database.AppDatabaseRepository
import com.example.testsubsmanager.database.dto.Currency
import com.example.testsubsmanager.database.dto.Subscription
import com.example.testsubsmanager.database.dto.TypeDuration
import com.example.testsubsmanager.services.currency.CurrencyRetrofitClient
import com.example.testsubsmanager.services.currency.ValCurs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject


class MainViewModel @Inject constructor(private val repository: AppDatabaseRepository) : ViewModel() {

    private val subscriptionsLiveData = MutableLiveData<List<Subscription>>()
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    fun getAllSubscriptions(): LiveData<List<Subscription>> {
        ioScope.launch {
            val subscriptions = repository.getAllSubscriptions()
            subscriptionsLiveData.postValue(subscriptions.value)
        }
        return subscriptionsLiveData
    }

    fun saveSubscription(subscriptionName: String,
                         color: String = "#FFFFFF",
                         price: Double = 0.0,
                         currency: String = "RUB",
                         startDate: String = LocalDate.now().toString(),
                         duration: Int = 1,
                         typeDuration: String = "Months") {
        val liveData: LiveData<Currency> = repository.getCurrencyByCode(currency)
        val subCurrency: Currency? = liveData.value
        val formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu", Locale.ENGLISH)
        val subStartDate: LocalDate = LocalDate.parse(startDate, formatter)
        val renewalDate: LocalDate = calculateRenewalDate(subStartDate, duration, typeDuration.uppercase())
        if (subCurrency != null) {

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


    suspend fun fetchAndSaveCurrencyRates(date: String) {
        try {
            val valCurs = CurrencyRetrofitClient.getApi().getCurrencyRates(date)
            val currenciesCBRs = valCurs.valutes

            currenciesCBRs.forEach { currencyCBR ->
                val exchangeRate = currencyCBR.value.toDouble()
                val adjustedExchangeRate = exchangeRate / currencyCBR.nominal.toDouble()

                val existingCurrency = repository.getCurrencyByCode(currencyCBR.charCode)

                if (existingCurrency != null) {
                    val updatedCurrency = existingCurrency.value!!.copy(
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
                        mainScope.launch { repository.insertCurrency(newCurrency) }
                    }
                }
            }
        } catch (e: Exception) {
            // Handle exception
        }
    }

}
