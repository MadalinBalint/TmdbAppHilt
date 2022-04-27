package com.mendelin.tmdb_hilt.data.room

import androidx.room.TypeConverter

class ConverterIntList {
    @TypeConverter
    fun fromList(list: List<Int>): String = list.joinToString(",")

    @TypeConverter
    fun toList(s: String): List<Int> {
        return s.split(",")
            .map { it.toInt() }
    }
}