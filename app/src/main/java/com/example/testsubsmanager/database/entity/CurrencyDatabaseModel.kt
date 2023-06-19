package com.example.testsubsmanager.database.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyDatabaseModel(
    @PrimaryKey
    val code: String,
    val name: String,
    val symbol: String,
    @ColumnInfo(name = "exchange_rate") val exchangeRate: Double
)
