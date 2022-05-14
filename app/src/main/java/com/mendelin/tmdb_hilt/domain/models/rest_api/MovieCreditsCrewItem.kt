package com.mendelin.tmdb_hilt.domain.models.rest_api

import androidx.annotation.Keep

@Keep
data class MovieCreditsCrewItem(
    val id: Int,
    val department: String,
    val original_language: String,
    val original_title: String,
    val job: String,
    val overview: String,
    val vote_count: Int,
    val video: Boolean,
    val poster_path: String?,
    val backdrop_path: String?,
    val title: String,
    val popularity: Float,
    val genre_ids: List<Int>,
    val vote_average: Float,
    val adult: Boolean,
    val release_date: String,
    val credit_id: String,
)