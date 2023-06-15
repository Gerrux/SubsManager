package com.example.testsubsmanager.data.databases
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyDatabaseModel(
    @PrimaryKey val code: String,
    val name: String,
    val symbol: String,
    val exchangeRate: Double
)
