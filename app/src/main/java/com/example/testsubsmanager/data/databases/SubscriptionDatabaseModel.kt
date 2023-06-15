package com.example.testsubsmanager.data.databases
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "subscriptions")
data class SubscriptionDatabaseModel(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val renewalDate: LocalDate,
    val isAutoRenew: Boolean,
    val paymentMethod: String
)
