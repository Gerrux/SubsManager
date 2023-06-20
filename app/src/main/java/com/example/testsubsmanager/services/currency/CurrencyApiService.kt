package com.example.testsubsmanager.services.currency

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("scripts/XML_daily.asp")
    suspend fun getCurrencyRates(
        @Query("date_req") date: String
    ): ValCurs
}