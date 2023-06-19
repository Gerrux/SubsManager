package com.example.testsubsmanager.database.entity
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "notifications")
data class NotificationDatabaseModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notification_id") val notificationId: Long,
    val title: String,
    val message: String,
    @Embedded val subscription: SubscriptionDatabaseModel,
    val date: LocalDate
)

