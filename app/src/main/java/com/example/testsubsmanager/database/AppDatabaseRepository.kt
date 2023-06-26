package com.example.testsubsmanager.database

import android.content.Context
import com.example.testsubsmanager.database.dao.CurrencyDao
import com.example.testsubsmanager.database.dao.NotificationDao
import com.example.testsubsmanager.database.dao.SubscriptionDao
import com.example.testsubsmanager.database.dto.Currency
import com.example.testsubsmanager.database.dto.Notification
import com.example.testsubsmanager.database.dto.Subscription
import com.example.testsubsmanager.database.mappers.CurrencyMapper
import com.example.testsubsmanager.database.mappers.NotificationMapper
import com.example.testsubsmanager.database.mappers.SubscriptionMapper
import java.time.LocalDate

class AppDatabaseRepository(database: AppDatabase)  {

    private val subscriptionDao: SubscriptionDao = database.subscriptionDao()
    private val notificationDao: NotificationDao = database.notificationDao()
    private val currencyDao: CurrencyDao = database.currencyDao()

    companion object {
        private var INSTANCE: AppDatabaseRepository? = null

        fun get(context: Context): AppDatabaseRepository {
            if (INSTANCE == null) {
                INSTANCE = AppDatabaseRepository(AppDatabase.get(context))
            }
            return INSTANCE as AppDatabaseRepository
        }
    }

    suspend fun insertSubscription(subscription: Subscription) {
        subscriptionDao.insert(SubscriptionMapper.toEntity(subscription))
    }

    suspend fun updateSubscription(subscription: Subscription) {
        subscriptionDao.update(SubscriptionMapper.toEntity(subscription))
    }

    suspend fun deleteSubscription(subscription: Subscription) {
        subscriptionDao.delete(SubscriptionMapper.toEntity(subscription))
    }

    fun getAllSubscriptions(): List<Subscription> {
        return subscriptionDao.getAllSubscriptions().map { SubscriptionMapper.toDTO(it) }
    }

    fun getSubscriptionById(id: Int): Subscription {
        return SubscriptionMapper.toDTO(subscriptionDao.getSubscriptionById(id))
    }

    fun getAllNotifications(): List<Notification> {
        return notificationDao.getAllNotifications().map { NotificationMapper.toDTO(it) }
    }

    suspend fun insertNotification(notification: Notification) {
        notificationDao.insert(NotificationMapper.toEntity(notification))
    }

    suspend fun deleteNotification(notification: Notification) {
        notificationDao.insert(NotificationMapper.toEntity(notification))
    }


    suspend fun insertCurrency(currency: Currency) {
        currencyDao.insert(CurrencyMapper.toEntity(currency))
    }

    suspend fun updateCurrency(currency: Currency) {
        currencyDao.update(CurrencyMapper.toEntity(currency))
    }

    fun getAllCurrencies(): List<Currency> {
        return currencyDao.getAllCurrencies().map { CurrencyMapper.toDTO(it) }
    }

    fun getCurrencyByCode(code: String): Currency {
        return CurrencyMapper.toDTO(currencyDao.getCurrencyByCode(code))
    }
    fun getCurrenciesByCodes(listCodes: List<String>): List<Currency> {
        return currencyDao.getCurrenciesByCodes(listCodes).map { CurrencyMapper.toDTO(it) }
    }

    fun getLastIdNotification(): Int {
        return notificationDao.getLastIdNotification()
    }

    fun getSubscriptionsByRenewalDate(renewalDate: LocalDate): List<Subscription> {
        return subscriptionDao.getSubscriptionsByRenewalDate(renewalDate).map { SubscriptionMapper.toDTO(it) }
    }


}