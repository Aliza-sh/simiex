package com.aliza.simiex.utils.extensions

import com.aliza.simiex.utils.date.TimeUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.convertDateToFarsiWithTime(): String {
    val dateTimeSplit = this.split(" ")

    if (dateTimeSplit.size != 2) {
        throw IllegalArgumentException("Invalid date-time format")
    }

    val dateSplit = dateTimeSplit[0].split("-")
    val timeSplit = dateTimeSplit[1].split(":")

    if (dateSplit.size != 3 || timeSplit.size != 2) {
        throw IllegalArgumentException("Invalid date-time format")
    }

    // تبدیل مقادیر به عدد
    val year = dateSplit[0].toInt()
    val month = dateSplit[1].toInt()
    val day = dateSplit[2].toInt()
    val hour = timeSplit[0].toInt()
    val minute = timeSplit[1].toInt()

    val timeUtils = TimeUtils(year, month, day)

    val dayOfWeek = timeUtils.getWeekDayStr()

    return "$dayOfWeek، $hour:$minute"
}

fun getTodayDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return dateFormat.format(Date())
}