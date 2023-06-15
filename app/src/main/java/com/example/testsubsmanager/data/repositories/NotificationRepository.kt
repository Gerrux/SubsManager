package com.example.testsubsmanager.data.repositories
import androidx.lifecycle.LiveData
import com.example.testsubsmanager.dao.NotificationDao
import com.example.testsubsmanager.data.models.Notification

class NotificationRepository(private val notificationDao: NotificationDao) {

    val allNotifications: LiveData<List<Notification>> = notificationDao.getAllNotifications()

    suspend fun insert(notification: Notification) {
        notificationDao.insert(notification)
    }

    suspend fun update(notification: Notification) {
        notificationDao.update(notification)
    }

    suspend fun delete(notification: Notification) {
        notificationDao.delete(notification)
    }

    fun getNotificationById(id: Int): LiveData<Notification> {
        return notificationDao.getNotificationById(id)
    }

    fun getUnreadNotifications(): LiveData<List<Notification>> {
        return notificationDao.getUnreadNotifications()
    }
}
