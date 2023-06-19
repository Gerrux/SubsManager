package com.example.testsubsmanager.database.dto

import java.time.LocalDate

data class Notification(
    val notificationId: Long,
    val title: String,
    val message: String,
    val subscription: Subscription,
    val date: LocalDate
)
