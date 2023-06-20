package com.example.testsubsmanager.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testsubsmanager.R
import com.example.testsubsmanager.database.dto.Currency

class CurrencyListAdapter(var currencies: List<Currency>) : RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {
    private var selectedItemPosition = RecyclerView.NO_POSITION
    private var onItemClickListener: ((Currency) -> Unit)? = null
    @SuppressLint("NotifyDataSetChanged")
    inner class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Declare views in the item layout
        private val currencyCodeTextView: TextView = itemView.findViewById(R.id.currencyCodeTextView)
        private val currencyNameTextView: TextView = itemView.findViewById(R.id.currencyNameTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition

                Log.e("SB", "onItemClickListener: $onItemClickListener")
                if (position != RecyclerView.NO_POSITION) {
                    selectedItemPosition = position
                    notifyDataSetChanged()
                    val currency = currencies[selectedItemPosition]

                    onItemClickListener?.invoke(currency)
                }
            }
        }


        fun bind(currency: Currency, isSelected: Boolean) {
            currencyCodeTextView.text = currency.code
            currencyNameTextView.text = currency.name
            itemView.isSelected = isSelected
        }
    }

    fun setOnItemClickListener(listener: (Currency) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_currency, parent, false)
        return CurrencyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencies[position]
        val isSelected = position == selectedItemPosition
        holder.bind(currency, isSelected)
    }

    override fun getItemCount(): Int {
        return currencies.size
    }
}
