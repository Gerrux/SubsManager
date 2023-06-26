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
import java.time.temporal.ChronoUnit

class SubscriptionListAdapter(var subscriptions: List<Subscription>) :
    RecyclerView.Adapter<SubscriptionListAdapter.SubscriptionViewHolder>() {
    private var selectedItemPosition = RecyclerView.NO_POSITION
    private var onItemClickListener: ((Subscription) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    inner class SubscriptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Declare views in the item layout
        private val subscriptionNameTextView: TextView =
            itemView.findViewById(R.id.subscriptionNameTextView)
        private val subscriptionPriceTextView: TextView =
            itemView.findViewById(R.id.subscriptionPriceTextView)
        private val subscriptionRenewalDateTextView: TextView =
            itemView.findViewById(R.id.subscriptionRenewalDateTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    selectedItemPosition = position
                    notifyDataSetChanged()
                    val subscription = subscriptions[selectedItemPosition]

                    onItemClickListener?.invoke(subscription)
                }
            }
        }


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
            subscriptionRenewalDateTextView.text = getRenewalDateText(
                subscription.startDate,
                Pair(subscription.duration, subscription.typeDuration)
            )
            subscriptionRenewalDateTextView.setTextColor(textColor)
            itemView.background.setTint(backgroundColor)
            itemView.isSelected = isSelected
        }

        private fun getRenewalDateText(
            startDate: LocalDate,
            duration: Pair<Int, TypeDuration>
        ): String {
            val currentDate = LocalDate.now()
            var paymentDueDate = startDate

            var numberOfPayments: Int = when (duration.second) {
                TypeDuration.DAYS -> ChronoUnit.DAYS.between(startDate, currentDate)
                    .toInt() / duration.first

                TypeDuration.WEEKS -> ChronoUnit.WEEKS.between(startDate, currentDate)
                    .toInt() / duration.first

                TypeDuration.MONTHS -> ChronoUnit.MONTHS.between(startDate, currentDate)
                    .toInt() / duration.first

                TypeDuration.YEARS -> ChronoUnit.YEARS.between(startDate, currentDate)
                    .toInt() / duration.first
            }

            numberOfPayments += 1

            when (duration.second) {
                TypeDuration.DAYS -> paymentDueDate =
                    paymentDueDate.plusDays(duration.first.toLong() * numberOfPayments)

                TypeDuration.WEEKS -> paymentDueDate =
                    paymentDueDate.plusWeeks(duration.first.toLong() * numberOfPayments)

                TypeDuration.MONTHS -> paymentDueDate =
                    paymentDueDate.plusMonths(duration.first.toLong() * numberOfPayments)

                TypeDuration.YEARS -> paymentDueDate =
                    paymentDueDate.plusYears(duration.first.toLong() * numberOfPayments)
            }
            val period = Period.between(currentDate, paymentDueDate)
            Log.d("GRRX", "$period $paymentDueDate")
            return when {
                period.years > 0 -> "Payment due in ${period.years} year${if (period.years > 1) "s" else ""}"
                period.months > 0 -> "Payment due in ${period.months} month${if (period.months > 1) "s" else ""}"
                period.days > 0 -> "Payment due in ${period.days} day${if (period.days > 1) "s" else ""}"
                else -> "Payment due today"
            }
        }

        fun getContrastingColor(background: Int): Int {
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

    fun setOnItemClickListener(listener: (Subscription) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_subscription, parent, false)
        return SubscriptionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {
        val subscription = subscriptions[position]
        val isSelected = position == selectedItemPosition
        holder.bind(subscription, isSelected)
    }

    override fun getItemCount(): Int {
        return subscriptions.size
    }
}