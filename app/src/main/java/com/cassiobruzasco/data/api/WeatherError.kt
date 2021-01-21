package com.cassiobruzasco.data.api

import com.google.gson.annotations.SerializedName
import okhttp3.internal.http2.ErrorCode

/**
 * Data class for formatting and handling errors that come from bad requests
 *
 * @param code HTTP Error code
 * @param detail Error message received from destination
 *
 */
data class WeatherError(
    @SerializedName("cod") val code: ErrorCode?,
    @SerializedName("message") val detail: String?
)