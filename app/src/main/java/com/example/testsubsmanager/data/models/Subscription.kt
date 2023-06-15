package com.example.testsubsmanager.data.models

import java.time.LocalDate

data class Subscription(
    val id: Int,
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


