package com.example.testsubsmanager.services.notification

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.testsubsmanager.R
import com.example.testsubsmanager.database.AppDatabaseRepository
import com.example.testsubsmanager.database.dto.Notification
import com.example.testsubsmanager.database.dto.Subscription
import com.example.testsubsmanager.di.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class NotificationService : Service() {

    @Inject
    lateinit var repository: AppDatabaseRepository

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().build().inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val currentDate = LocalDate.now()

        val subscriptions = repository.getSubscriptionsByRenewalDate(currentDate.plusDays(1))

        if (subscriptions.isNotEmpty()) {
            for (subscription in subscriptions) {
                CoroutineScope(Dispatchers.IO).launch {
                    val notification = createNotification(subscription, currentDate)

                    val notificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                    CoroutineScope(Dispatchers.Main).launch {
                        notificationManager.notify(
                            notification.second,
                            notification.first.build()
                        )
                    }
                }
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private suspend fun createNotification(
        subscription: Subscription,
        currentDate: LocalDate
    ): Pair<NotificationCompat.Builder, Int> {
        val message = "Your subscription is expiring soon"
        val notification = withContext(Dispatchers.IO) {
            val notificationId = repository.getLastIdNotification() + 1
            val notification = Notification(
                    notificationId = notificationId,
                    title = subscription.nameSub,
                    message = message,
                    subscription = subscription,
                    date = currentDate
            )
            CoroutineScope(Dispatchers.Main).launch {
                    repository.insertNotification(notification)
            }
            notification
        }


        // Добавьте дополнительные настройки уведомления, если необходимо
        // Например, установите звук, вибрацию или действия
        val builder = NotificationCompat.Builder(this, "SubManager Channel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(subscription.nameSub)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        return Pair(builder, notification.notificationId)
    }
}