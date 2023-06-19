package com.example.testsubsmanager.database.dto

import java.time.LocalDate

data class Subscription(
    val id: Int = 0,
    val nameSub: String,
    val color: String,
    val price: Double,
    val currency: Currency,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val renewalDate: LocalDate,
    val duration: Int,
    val typeDuration: TypeDuration,
)