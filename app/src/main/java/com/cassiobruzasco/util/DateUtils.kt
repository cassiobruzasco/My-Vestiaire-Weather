package com.cassiobruzasco.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Class to handle date format
 */
class DateUtil {

    /**
     * Current Locale
     */
    object LocaleUtil {
        val default: Locale
            get() = Locale.getDefault()
    }

    /**
     * Format seconds to dd MMMM
     * @param seconds Timestamp as seconds
     * @return formatted string
     */
    fun to_dd_MMMM(seconds: Long): String {
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

    /**
     * Get what time of the day based on singleton calendar instance
     * @return Time of the day in 24 hour format
     */
    fun getTimeOfDay(): Int {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    }
}