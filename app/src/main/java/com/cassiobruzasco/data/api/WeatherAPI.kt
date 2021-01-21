package com.cassiobruzasco.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// My main call for getting daily forecast
interface WeatherAPI {
    @GET("daily")
    suspend fun getWeather(
        @Query("q") location: String,
        @Query("mode") mode: String,
        @Query("units") units: String,
        @Query("cnt") count: Int,
        @Query("appid") apiKey: String
    ): Response<WeatherResponseItem>
}