package com.example.testsubsmanager.ui.models

import com.example.testsubsmanager.database.dto.Subscription

class AnalyticsModel(
    val month: String,
    val amountString: String,
    val amount: Double,
    var height: Int,
    val listSubscription: List<Subscription>
) {
    fun getAbbreviatedMonthString(): String {
        return this.month.substring(0, 3)
    }
}