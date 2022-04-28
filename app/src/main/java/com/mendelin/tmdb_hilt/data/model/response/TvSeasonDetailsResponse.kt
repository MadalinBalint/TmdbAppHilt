package com.mendelin.tmdb_hilt.data.model.response

import androidx.annotation.Keep
import com.mendelin.tmdb_hilt.data.model.rest_api.EpisodeItem

@Keep
data class TvSeasonDetailsResponse(
    val _id: String,
    val air_date: String,
    val episodes: List<EpisodeItem>,
    val name: String,
    val overview: String,
    val id: Int,
    val poster_path: String?,
    val season_number: Int,

    val status_message: String = "",
    val success: Boolean = true,
    val status_code: Int = 200
)