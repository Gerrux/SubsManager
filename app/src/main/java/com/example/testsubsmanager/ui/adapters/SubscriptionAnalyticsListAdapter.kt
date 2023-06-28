package com.example.testsubsmanager.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testsubsmanager.R
import com.example.testsubsmanager.database.dto.Subscription
import com.example.testsubsmanager.database.dto.TypeDuration
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class SubscriptionAnalyticsListAdapter(var subscriptions: List<Subscription>) :
    RecyclerView.Adapter<SubscriptionAnalyticsListAdapter.SubscriptionAnalyticsViewHolder>() {
    private var selectedItemPosition = RecyclerView.NO_POSITION

    inner class SubscriptionAnalyticsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val subscriptionNameTextView: TextView =
            itemView.findViewById(R.id.subscriptionNameTextView)
        private val subscriptionPriceTextView: TextView =
            itemView.findViewById(R.id.subscriptionPriceTextView)
        private val subscriptionRenewalDateTextView: TextView =
            itemView.findViewById(R.id.subscriptionRenewalDateTextView)


        @SuppressLint("SetTextI18n")
        fun bind(subscription: Subscription, isSelected: Boolean) {
            val backgroundColor = Color.parseColor(subscription.color)
            val textColor = getContrastingColor(backgroundColor)
            val decimalFormat = DecimalFormat("#.##")
            val priceSubscription = decimalFormat.format(subscription.price)
            subscriptionNameTextView.text = subscription.nameSub
            subscriptionNameTextView.setTextColor(textColor)
            subscriptionPriceTextView.text = "$priceSubscription ${subscription.currency.code}"
            subscriptionPriceTextView.setTextColor(textColor)
            subscriptionRenewalDateTextView.text = subscription.renewalDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
            subscriptionRenewalDateTextView.setTextColor(textColor)
            itemView.background.setTint(backgroundColor)
            itemView.isSelected = isSelected
        }

        private fun getContrastingColor(background: Int): Int {
            val red = Color.red(background)
            val green = Color.green(background)
            val blue = Color.blue(background)
            val luminance = (red * 0.299 + green * 0.587 + blue * 0.114) / 255
            return if (luminance > 0.5) {
                Color.BLACK
            } else {
                Color.WHITE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionAnalyticsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_subscription, parent, false)
        return SubscriptionAnalyticsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SubscriptionAnalyticsViewHolder, position: Int) {
        val subscription = subscriptions[position]
        val isSelected = position == selectedItemPosition
        holder.bind(subscription, isSelected)
    }

    override fun getItemCount(): Int {
        return subscriptions.size
    }
}