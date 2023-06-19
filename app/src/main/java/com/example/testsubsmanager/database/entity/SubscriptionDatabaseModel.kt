package com.example.testsubsmanager.database.entity
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "subscriptions")
data class SubscriptionDatabaseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name_sub") val nameSub: String,
    val color: String,
    val price: Double,
    @Embedded val currency: CurrencyDatabaseModel,
    @ColumnInfo(name = "start_date") val startDate: LocalDate,
    @ColumnInfo(name = "end_date") val endDate: LocalDate,
    @ColumnInfo(name = "renewal_date") val renewalDate: LocalDate,
    val duration: Int,
    @ColumnInfo(name = "type_duration") val typeDuration: String,
)
