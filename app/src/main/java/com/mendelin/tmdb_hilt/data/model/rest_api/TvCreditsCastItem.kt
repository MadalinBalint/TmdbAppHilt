package com.mendelin.tmdb_hilt.data.model.rest_api

import androidx.annotation.Keep

@Keep
data class TvCreditsCastItem(
    val credit_id: String,
    val original_name: String,
    val id: Int,
    val genre_ids: List<Int>,
    val character: String,
    val name: String,
    val poster_path: String?,
    val vote_count: Int,
    val vote_average: Float,
    val popularity: Float,
    val episode_count: Int,
    val original_language: String,
    val first_air_date: String,
    val backdrop_path: String?,
    val overview: String,
    val origin_country: List<String>
)