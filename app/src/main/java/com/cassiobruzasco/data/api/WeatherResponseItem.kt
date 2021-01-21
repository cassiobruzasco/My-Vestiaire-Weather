package com.cassiobruzasco.data.api

import com.google.gson.annotations.SerializedName

// My model for daily weather response
// Here I map every single json object
data class WeatherResponseItem(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)