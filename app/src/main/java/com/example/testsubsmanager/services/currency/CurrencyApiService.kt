package com.example.testsubsmanager.services.currency

import org.simpleframework.xml.Root
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

@Root(name = "ValCurs")
interface CurrencyApiService {
    @Headers("Content-Type: text/xml", "Accept-Charset: utf-8")
    @GET("scripts/XML_daily_eng.asp")
    suspend fun getCurrencyRates(
        @Query("date_req") date: String
    ): Response<ValCurs>
}