package com.mendelin.tmdb_hilt.data.model.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mendelin.tmdb_hilt.data.room.ConverterIntList

@Keep
@Entity(tableName = "favorite_movies")
data class MovieListResultEntity(
    val poster_path: String?,
    val adult: Boolean,
    val overview: String,
    val release_date: String,
    @TypeConverters(ConverterIntList::class)
    val genre_ids: List<Int>,
    @PrimaryKey val id: Int,
    val original_title: String,
    val original_language: String,
    val title: String,
    val backdrop_path: String?,
    val popularity: Float,
    val vote_count: Int,
    val video: Boolean,
    val vote_average: Float
) : FavoriteEntity()