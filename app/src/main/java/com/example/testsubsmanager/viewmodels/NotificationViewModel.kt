package com.example.testsubsmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testsubsmanager.data.models.Notification

class NotificationViewModel : ViewModel() {
    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> = _notifications

    init {
        // load notifications from a data source and set the value of _notifications
    }

    fun markAsRead(notification: Notification) {
        // update the data source and the value of _notifications
    }
}