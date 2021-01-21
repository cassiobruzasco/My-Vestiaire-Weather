package com.cassiobruzasco.data.api

import com.cassiobruzasco.data.api.BaseConfig.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton retrofit instance
 */
object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: WeatherAPI by lazy {
        retrofit.create(WeatherAPI::class.java)
    }
}