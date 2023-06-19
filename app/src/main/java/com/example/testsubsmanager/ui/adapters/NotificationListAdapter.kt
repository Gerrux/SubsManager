package com.example.testsubsmanager.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.testsubsmanager.R
import com.example.testsubsmanager.database.dto.Notification

class NotificationListAdapter(private val notifications: List<Notification>) :
    RecyclerView.Adapter<NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        holder.bind(notification)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }
}

class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val notificationTitle: TextView = itemView.findViewById(R.id.notificationTitle)
    private val notificationMessage: TextView = itemView.findViewById(R.id.notificationMessage)

    fun bind(notification: Notification) {
        notificationTitle.text = notification.title
        notificationMessage.text = notification.message
        // set other views in the layout
    }
}