package com.example.testsubsmanager.data.models

import java.time.LocalDate

data class Notification(
    val id: Int,
    val title: String,
    val message: String,
    val imageUrl: String,
    val date: LocalDate,
    val isRead: Boolean
)
