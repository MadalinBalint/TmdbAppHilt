package com.mendelin.tmdb_hilt.data.room

import androidx.room.TypeConverter

class ConverterStringList {
    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString(",")

    @TypeConverter
    fun toList(s: String): List<String> {
        return s.split(",")
            .map { it }
    }
}