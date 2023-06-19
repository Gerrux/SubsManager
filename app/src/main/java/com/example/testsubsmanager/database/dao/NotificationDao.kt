package com.example.testsubsmanager.database.dao
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testsubsmanager.database.dto.Notification
import com.example.testsubsmanager.database.entity.NotificationDatabaseModel


@Dao
interface NotificationDao {

    @Transaction
    @Query("SELECT * FROM notifications")
    fun getAllNotifications(): LiveData<List<NotificationDatabaseModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: NotificationDatabaseModel)

    @Update
    suspend fun update(notification: NotificationDatabaseModel)

    @Delete
    suspend fun delete(notification: NotificationDatabaseModel)

    @Query("SELECT * FROM notifications WHERE notification_id = :notificationId")
    fun getNotificationById(notificationId: Int): LiveData<NotificationDatabaseModel>
}
