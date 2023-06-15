package com.example.testsubsmanager.dao
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testsubsmanager.data.models.Subscription
import java.time.LocalDate

@Dao
interface SubscriptionDao {

    @Query("SELECT * FROM subscriptions ORDER BY renewalDate ASC")
    fun getAllSubscriptions(): LiveData<List<Subscription>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subscription: Subscription)

    @Update
    suspend fun update(subscription: Subscription)

    @Delete
    suspend fun delete(subscription: Subscription)

    @Query("SELECT * FROM subscriptions WHERE id = :id")
    fun getSubscriptionById(id: Int): LiveData<Subscription>

    @Query("SELECT * FROM subscriptions WHERE renewalDate = :date ORDER BY renewalDate ASC")
    fun getSubscriptionsByRenewalDate(date: LocalDate): LiveData<List<Subscription>>
}
