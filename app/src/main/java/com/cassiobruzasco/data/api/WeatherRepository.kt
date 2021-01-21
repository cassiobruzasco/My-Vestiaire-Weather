package com.cassiobruzasco.data.api

/**
 * This is the Weather Repository, it inherits our handleResponse method
 * Checking if there is a mapped error before delivering our object
 */
interface WeatherRepository: Repository {
    suspend fun getWeather(
        q: String,
        count: Int
    ): Result<WeatherResponseItem>
}

open class WeatherRepositoryImpl : BaseRepository(), WeatherRepository {

    private var isGetWeatherDirty = false

    override suspend fun getWeather(q: String, count: Int): Result<WeatherResponseItem> {
        val cacheTime = if (isGetWeatherDirty) {
            BaseConfig.CacheTimeout.NONE
        } else {
            BaseConfig.CacheTimeout.DEFAULT
        }
        val response = handleResponse(errorBodyType = WeatherError::class.java) {
            RetrofitInstance.api.getWeather(
                location = q,
                mode = BaseConfig.MODE,
                units = BaseConfig.UNITS,
                count = count,
                apiKey = BaseConfig.API_KEY,
                cacheTime = cacheTime
            )
        }

        if (response.isSuccess) isGetWeatherDirty = false
        return response
    }
}