package com.mendelin.tmdb_hilt.data.model.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mendelin.tmdb_hilt.data.room.ConverterIntList
import com.mendelin.tmdb_hilt.data.room.ConverterStringList

@Keep
@Entity(tableName = "favorite_tvshows")
data class TvListResultItem(
    val poster_path: String?,
    val popularity: Float,
    @PrimaryKey val id: Int,
    val backdrop_path: String?,
    val vote_average: Float,
    val overview: String,
    val first_air_date: String,
    @TypeConverters(ConverterStringList::class)
    val origin_country: List<String>,
    @TypeConverters(ConverterIntList::class)
    val genre_ids: List<Int>,
    val original_language: String,
    val vote_count: Int,
    val name: String,
    val original_name: String
) : FavoriteItem()