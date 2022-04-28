package com.mendelin.tmdb_hilt.data.model.rest_api

import androidx.annotation.Keep

@Keep
data class EpisodeItem(
    val air_date: String,
    val episode_number: Int,
    val crew: List<CrewItem>,
    val guest_stars: List<CastItem>,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String,
    val season_number: Int,
    val still_path: String,
    val vote_average: Float,
    val vote_count: Int
)