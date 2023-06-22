package com.example.testsubsmanager.database.dao
import androidx.room.*
import com.example.testsubsmanager.database.entity.SubscriptionDatabaseModel
import java.time.LocalDate

@Dao
interface SubscriptionDao {

    @Transaction
    @Query("SELECT * FROM subscriptions")
    fun getAllSubscriptions(): List<SubscriptionDatabaseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subscription: SubscriptionDatabaseModel)

    @Update
    suspend fun update(subscription: SubscriptionDatabaseModel)

    @Delete
    suspend fun delete(subscription: SubscriptionDatabaseModel)

    @Query("SELECT * FROM subscriptions WHERE id = :id")
    fun getSubscriptionById(id: Int): SubscriptionDatabaseModel

    @Query("SELECT * FROM subscriptions WHERE renewal_date = :renewalDate ORDER BY renewal_date ASC")
    fun getSubscriptionsByRenewalDate(renewalDate: LocalDate): List<SubscriptionDatabaseModel>
}
