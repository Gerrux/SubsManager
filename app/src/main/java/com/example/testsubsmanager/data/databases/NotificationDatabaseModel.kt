package com.example.testsubsmanager.data.databases
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "notifications")
data class NotificationDatabaseModel(
    @PrimaryKey val id: Int,
    val title: String,
    val message: String,
    val imageUrl: String,
    val date: LocalDate,
    val isRead: Boolean
)
