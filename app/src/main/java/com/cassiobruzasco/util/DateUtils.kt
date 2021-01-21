package com.cassiobruzasco.util

import java.text.SimpleDateFormat
import java.util.*

// This class gets the date as seconds and change to a better understanding format
class DateUtil {

    object LocaleUtil {
        val default: Locale
            get() = Locale.FRANCE
    }

    fun to_MM_yyyy(millis: Long): String {
        return try {
            require(millis != 0L)
            val apiDate = Date(millis)

            val outputFormat = "MM/yyyy"
            val outputSdf = SimpleDateFormat(outputFormat, LocaleUtil.default)

            outputSdf.format(apiDate)
        } catch (e: Exception) {
            "-"
        }
    }
}