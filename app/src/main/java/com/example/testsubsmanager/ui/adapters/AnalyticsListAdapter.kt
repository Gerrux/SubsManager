package com.example.testsubsmanager.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.testsubsmanager.R
import com.example.testsubsmanager.database.dto.Subscription
import com.example.testsubsmanager.ui.models.AnalyticsModel
import java.text.DecimalFormat
import java.util.Locale

class AnalyticsListAdapter(var analyticsModels: List<AnalyticsModel>) :
    RecyclerView.Adapter<AnalyticsListAdapter.AnalyticViewHolder>() {
    private var selectedItemPosition = RecyclerView.NO_POSITION
    private var onItemClickListener: ((AnalyticsModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    inner class AnalyticViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val analyticsMonthTitle: TextView = itemView.findViewById(R.id.analytics_month_title)
        private val currentMonthAmount: TextView = itemView.findViewById(R.id.current_month_amount)
        private val topBgCapsule: ImageView = itemView.findViewById(R.id.top_bg_capsule)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    selectedItemPosition = position
                    notifyDataSetChanged()
                    val analyticModel = analyticsModels[selectedItemPosition]

                    onItemClickListener?.invoke(analyticModel)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(analyticsModel: AnalyticsModel, isSelected: Boolean) {
            analyticsMonthTitle.text = analyticsModel.getAbbreviatedMonthString().capitalize(Locale.ROOT)
            currentMonthAmount.text = analyticsModel.amountString
            val layoutParams = topBgCapsule.layoutParams
            layoutParams.height = convertDpToPixels(topBgCapsule.context, analyticsModel.height)
            topBgCapsule.layoutParams = layoutParams
            itemView.isSelected = isSelected
        }
    }

    fun convertDpToPixels(context: Context, dp: Int) =
        (dp * context.resources.displayMetrics.density).toInt()

    fun setOnItemClickListener(listener: (AnalyticsModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalyticsListAdapter.AnalyticViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_analytics_months, parent, false)
        return AnalyticViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnalyticsListAdapter.AnalyticViewHolder, position: Int) {
        val analyticModel = analyticsModels[position]
        val isSelected = position == selectedItemPosition
        holder.bind(analyticModel, isSelected)
    }

    override fun getItemCount(): Int {
        return analyticsModels.size
    }
}