package com.example.testsubsmanager.viewmodels

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testsubsmanager.database.AppDatabaseRepository
import com.example.testsubsmanager.database.dto.Currency
import com.example.testsubsmanager.database.dto.Subscription
import com.example.testsubsmanager.database.dto.TypeDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
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
        val startDate: LocalDate = LocalDate.parse(startDate, formatter)
        val renewalDate: LocalDate = calculateRenewalDate(startDate, duration, typeDuration.uppercase())
        if (subCurrency != null) {

            val subscription = Subscription(
                nameSub = subscriptionName,
                color = color,
                price = price,
                currency = subCurrency,
                startDate = startDate,
                renewalDate = renewalDate,
                duration = duration,
                typeDuration = TypeDuration.valueOf(typeDuration.uppercase())
            )

            ioScope.launch {
                ioScope.launch {
                    repository.insertSubscription(subscription)
                }
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

}
