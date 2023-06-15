package com.example.testsubsmanager.data.models

data class Settings(
    val id: Int,
    val notificationEnabled: Boolean,
    val subscriptionReminderEnabled: Boolean,
    val currency: String,
    val language: String,
    val darkModeEnabled: Boolean
)

