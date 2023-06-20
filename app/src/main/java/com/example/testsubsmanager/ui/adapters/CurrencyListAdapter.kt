package com.example.testsubsmanager.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testsubsmanager.R
import com.example.testsubsmanager.database.dto.Currency

class CurrencyListAdapter(var currencies: List<Currency>) : RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {

    inner class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Declare views in the item layout
        private val currencyCodeTextView: TextView = itemView.findViewById(R.id.currencyCodeTextView)
        private val currencyNameTextView: TextView = itemView.findViewById(R.id.currencyNameTextView)

        fun bind(currency: Currency) {
            currencyCodeTextView.text = currency.code
            currencyNameTextView.text = currency.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_currency, parent, false)
        return CurrencyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencies[position]
        holder.bind(currency)
    }

    override fun getItemCount(): Int {
        return currencies.size
    }
}
