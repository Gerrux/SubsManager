package com.example.testsubsmanager.database.dto

import java.time.LocalDate

data class Notification(
    val notificationId: Int = 0,
    val title: String,
    val message: String,
    val subscription: Subscription,
    val date: LocalDate
)
