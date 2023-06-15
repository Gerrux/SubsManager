package com.example.testsubsmanager.dao
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testsubsmanager.data.models.Notification


@Dao
interface NotificationDao {

    @Query("SELECT * FROM notifications ORDER BY date DESC")
    fun getAllNotifications(): LiveData<List<Notification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: Notification)

    @Update
    suspend fun update(notification: Notification)

    @Delete
    suspend fun delete(notification: Notification)

    @Query("SELECT * FROM notifications WHERE id = :id")
    fun getNotificationById(id: Int): LiveData<Notification>

    @Query("SELECT * FROM notifications WHERE isRead = 0 ORDER BY date DESC")
    fun getUnreadNotifications(): LiveData<List<Notification>>
}
