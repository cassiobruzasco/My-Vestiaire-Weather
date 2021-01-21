package com.cassiobruzasco.data.api

import com.google.gson.annotations.SerializedName

// My model for daily weather response
// Here I map every single json object
data class WeatherResponseItem(
    @SerializedName("city") val city: CityModel,
    @SerializedName("cod") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("cnt") val count: Int,
    @SerializedName("list") val list: MutableList<DayModel>
)

data class CityModel(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val cityName: String,
    @SerializedName("coord") val coordinate: CoordinateModel,
    @SerializedName("country") val country: String,
    @SerializedName("population") val population: Long,
    @SerializedName("timezone") val timezone: Int
)

data class CoordinateModel(
    @SerializedName("lon") val longitude: Double,
    @SerializedName("lat") val latitude: Double
)

data class DayModel(
    @SerializedName("dt") val date: Long,
    @SerializedName("sunrise") val sunriseTime: Long,
    @SerializedName("sunset") val sunsetTime: Long,
    @SerializedName("temp") val temperature: TemperatureModel,
    @SerializedName("feels_like") val feelsLike: FeelsLikeModel,
    @SerializedName("pressure") val pressure: Long,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("weather") val weather: MutableList<WeatherItemModel>,
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val degrees: Double,
    @SerializedName("clouds") val clouds: Double,
    @SerializedName("pop") val pop: Double,
    @SerializedName("rain") val rain: Double
)

data class WeatherItemModel(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class FeelsLikeModel(
    @SerializedName("day") val day: Double,
    @SerializedName("night") val night: Double,
    @SerializedName("eve") val evening: Double,
    @SerializedName("morn") val morning: Double
)

data class TemperatureModel(
    @SerializedName("day") val day: Double,
    @SerializedName("min") val min: Double,
    @SerializedName("max") val max: Double,
    @SerializedName("night") val night: Double,
    @SerializedName("eve") val evening: Double,
    @SerializedName("morn") val morning: Double
)
