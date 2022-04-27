package com.mendelin.tmdb_hilt.data.model.entity

import androidx.annotation.Keep

@Keep
data class EpisodeToAirItem(
    val air_date: String,
    val episode_number: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String,
    val season_number: Int,
    val still_path: String?,
    val vote_average: Float,
    val vote_count: Int
)