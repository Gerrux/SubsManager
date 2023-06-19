package com.example.testsubsmanager.database.dto


data class Statistics(
    val id: Int,
    val subscriptionCount: Int,
    val notificationCount: Int,
    val totalRevenue: Double,
    val totalSavings: Double
)
