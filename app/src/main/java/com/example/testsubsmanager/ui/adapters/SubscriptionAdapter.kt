package com.example.testsubsmanager.ui.adapters

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testsubsmanager.database.dto.Subscription
import com.example.testsubsmanager.databinding.ListItemSubscriptionBinding
import java.time.LocalDate
import java.time.Period

//class SubscriptionAdapter : ListAdapter<Subscription, SubscriptionAdapter.SubscriptionViewHolder>(
//    SubscriptionDiffCallback()
//) {
//    private var subscriptions: List<Subscription>? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ListItemSubscriptionBinding.inflate(inflater, parent, false)
//        return SubscriptionViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//    override fun submitList(subscriptions: List<Subscription>?) {
//        this.subscriptions = subscriptions
//        notifyDataSetChanged()
//    }
//
//    inner class SubscriptionViewHolder(private val binding: ListItemSubscriptionBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(subscription: Subscription) {
//            binding.subscription = subscription
//            binding.executePendingBindings()
//
//            binding.root.setBackgroundColor(Color.parseColor(subscription.color))
//            binding.renewalTextView.text = getRenewalText(subscription.renewalDate)
//        }
//
//        private fun getRenewalText(renewalDate: LocalDate): String {
//            val currentDate = LocalDate.now()
//            val period = Period.between(currentDate, renewalDate)
//
//            return when {
//                period.years > 0 -> "До оплаты осталось ${period.years} года"
//                period.months > 0 -> "До оплаты осталось ${period.months} месяца"
//                period.days > 0 -> "До оплаты осталось ${period.days} дня"
//                else -> "Сегодня оплатить"
//            }
//        }
//    }
//}
//
//class SubscriptionDiffCallback : DiffUtil.ItemCallback<Subscription>() {
//    override fun areItemsTheSame(oldItem: Subscription, newItem: Subscription): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Subscription, newItem: Subscription): Boolean {
//        return oldItem == newItem
//    }
//}
