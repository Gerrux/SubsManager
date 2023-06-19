package com.example.testsubsmanager.database.mappers

import com.example.testsubsmanager.database.dto.Notification
import com.example.testsubsmanager.database.entity.NotificationDatabaseModel

object NotificationMapper : Mapper<NotificationDatabaseModel, Notification> {

    override fun toDTO(from: NotificationDatabaseModel): Notification {
        return Notification(
            notificationId = from.notificationId,
            title = from.title,
            message = from.message,
            subscription = SubscriptionMapper.toDTO(from.subscription),
            date = from.date
        )
    }

    override fun toEntity(from: Notification): NotificationDatabaseModel {
        return NotificationDatabaseModel(
            notificationId = from.notificationId,
            title = from.title,
            message = from.message,
            subscription = SubscriptionMapper.toEntity(from.subscription),
            date = from.date
        )
    }
}
