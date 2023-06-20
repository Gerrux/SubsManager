package com.example.testsubsmanager.services.currency

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object CurrencyRetrofitClient {
    private const val BASE_URL = "http://www.cbr.ru/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    private val apiService: CurrencyApiService by lazy {
        retrofit.create(CurrencyApiService::class.java)
    }

    fun getApi() :CurrencyApiService {
        return apiService
    }
}