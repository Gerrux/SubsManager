package com.example.testsubsmanager.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testsubsmanager.R
import com.example.testsubsmanager.database.dto.Currency
import com.example.testsubsmanager.database.dto.Subscription

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


        fun bind(subscription: Subscription, isSelected: Boolean) {
            subscriptionNameTextView.text = subscription.nameSub
            subscriptionPriceTextView.text = subscription.price.toString()
            subscriptionRenewalDateTextView.text = subscription.renewalDate.toString()
            itemView.isSelected = isSelected
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
