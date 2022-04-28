package com.mendelin.tmdb_hilt.data.model.rest_api

import androidx.annotation.Keep

@Keep
data class EpisodeCrewItem(
    val id: Int,
    val credit_id: String,
    val name: String,
    val department: String,
    val job: String,
    val profile_path: String?
)