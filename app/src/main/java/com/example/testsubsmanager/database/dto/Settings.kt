package com.example.testsubsmanager.database.dto

data class Settings(
    val id: Int,
    val notificationEnabled: Boolean,
    val currency: String,
    val language: String,
    val darkModeEnabled: Boolean
)

