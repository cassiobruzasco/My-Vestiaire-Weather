package com.cassiobruzasco.util

import java.text.SimpleDateFormat
import java.util.*

// This class gets the date as seconds and change to a better understanding format
class DateUtil {

    object LocaleUtil {
        val default: Locale
            get() = Locale.getDefault()
    }

    fun to_dd_MMM(seconds: Long): String {
        return try {
            val millis = seconds * 1000
            require(millis != 0L)
            val apiDate = Date(millis)

            val outputDayFormat = "dd"
            val outputDaySdf = SimpleDateFormat(outputDayFormat, LocaleUtil.default)

            val outputMonthFormat = "MMMM"
            val outputMonthSdf = SimpleDateFormat(outputMonthFormat, LocaleUtil.default)

            "${outputDaySdf.format(apiDate)}/${
                outputMonthSdf.format(apiDate).toLowerCase().capitalize()
            }"
        } catch (e: Exception) {
            "-"
        }
    }

    fun getTimeOfDay(): Int {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    }
}