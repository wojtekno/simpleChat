package com.example.simplechat.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class DaysHoursFormatter @Inject constructor() {

    fun getDay(timestamp: Long): String {
        val date = Date(timestamp)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val weekAgo = calendar.time
        val sdf = if (date.before(weekAgo)) {
            SimpleDateFormat("MMM d, EEE")
        } else {
            SimpleDateFormat("EEEE")
        }
        return sdf.format(date)
    }

    fun getHour(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm")
        val date = Date(timestamp)

        return sdf.format(date)
    }
}
