package com.example.testsubsmanager.database.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyDatabaseModel(
    @PrimaryKey
    val code: String,
    val name: String,
    @ColumnInfo(name = "exchange_rate") val exchangeRate: Double,
    @ColumnInfo(name = "quote_date") val quoteDate: String
)
