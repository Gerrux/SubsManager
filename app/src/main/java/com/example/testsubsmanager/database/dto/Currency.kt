package com.example.testsubsmanager.database.dto


data class Currency(
    val code: String,
    val name: String,
    val exchangeRate: Double,
    val quoteDate: String
)
