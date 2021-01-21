package com.cassiobruzasco.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The call for getting daily forecast
 */
interface WeatherAPI {

    /**
     * The call for getting daily forecast
     *
     * @param location City name
     * @param mode Data format, json or xml
     * @param units Units of measurement
     * @param count Number of forecast days
     * @param apiKey Open Weather API KEY
     *
     */
    @GET("daily")
    suspend fun getWeather(
        @Query("q") location: String,
        @Query("mode") mode: String,
        @Query("units") units: String,
        @Query("cnt") count: Int,
        @Query("appid") apiKey: String
    ): Response<WeatherResponseItem>
}