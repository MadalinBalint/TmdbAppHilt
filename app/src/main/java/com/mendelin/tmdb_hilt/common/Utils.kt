package com.mendelin.tmdb_hilt.common

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun String.getYear(): String {
        val sd = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sd.parse(this)
        val calendar = Calendar.getInstance()
        calendar.time = date!!
        return calendar.get(Calendar.YEAR).toString()
    }
}