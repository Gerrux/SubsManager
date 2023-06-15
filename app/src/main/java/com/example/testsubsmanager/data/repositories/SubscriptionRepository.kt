package com.example.testsubsmanager.data.repositories
import androidx.lifecycle.LiveData
import com.example.testsubsmanager.dao.SubscriptionDao
import com.example.testsubsmanager.data.models.Subscription
import java.time.LocalDate

class SubscriptionRepository(private val subscriptionDao: SubscriptionDao) {

    val allSubscriptions: LiveData<List<Subscription>> = subscriptionDao.getAllSubscriptions()

    suspend fun insert(subscription: Subscription) {
        subscriptionDao.insert(subscription)
    }

    suspend fun update(subscription: Subscription) {
        subscriptionDao.update(subscription)
    }

    suspend fun delete(subscription: Subscription) {
        subscriptionDao.delete(subscription)
    }

    fun getSubscriptionById(id: Int): LiveData<Subscription> {
        return subscriptionDao.getSubscriptionById(id)
    }

    fun getSubscriptionsByRenewalDate(date: LocalDate): LiveData<List<Subscription>> {
        return subscriptionDao.getSubscriptionsByRenewalDate(date)
    }
}
