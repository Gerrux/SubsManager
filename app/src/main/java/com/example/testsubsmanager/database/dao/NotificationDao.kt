package com.example.testsubsmanager.database.dao
import androidx.room.*
import com.example.testsubsmanager.database.entity.NotificationDatabaseModel


@Dao
interface NotificationDao {

    @Transaction
    @Query("SELECT * FROM notifications")
    fun getAllNotifications(): List<NotificationDatabaseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: NotificationDatabaseModel)

    @Update
    suspend fun update(notification: NotificationDatabaseModel)

    @Delete
    suspend fun delete(notification: NotificationDatabaseModel)

    @Query("SELECT MAX(notification_id) FROM notifications")
    fun getLastIdNotification(): Int

    @Query("SELECT * FROM notifications WHERE notification_id = :notificationId")
    fun getNotificationById(notificationId: Int): NotificationDatabaseModel
}
