package com.mendelin.tmdb_hilt.domain.models.rest_api

import androidx.annotation.Keep

@Keep
data class TvCreditsCrewItem(
    val id: Int,
    val department: String,
    val original_language: String,
    val episode_count: Int,
    val job: String,
    val overview: String,
    val origin_country: List<String>,
    val original_name: String,
    val genre_ids: List<Int>,
    val name: String,
    val first_air_date: String,
    val backdrop_path: String?,
    val popularity: Float,
    val vote_count: Int,
    val vote_average: Float,
    val poster_path: String?,
    val credit_id: String,
)