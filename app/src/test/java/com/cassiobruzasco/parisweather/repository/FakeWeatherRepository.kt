package com.cassiobruzasco.parisweather.repository

import com.cassiobruzasco.data.api.*

class FakeWeatherRepository : WeatherRepository {
    companion object {
        val exampleResult = WeatherResponseItem(
            CityModel(0, "Paris", CoordinateModel(0.0, 0.0), "FR", 0, 0),
            0, "", 0,
            mutableListOf(
                DayModel(
                    0, 0, 0,
                    TemperatureModel(0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                    FeelsLikeModel(0.0, 0.0, 0.0, 0.0),
                    0,
                    0,
                    mutableListOf(WeatherItemModel(0, "", "", "")),
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                )
            )
        )
    }

    override suspend fun getWeather(q: String, count: Int): Result<WeatherResponseItem> {
        return Result.success(
            exampleResult
        )
    }
}