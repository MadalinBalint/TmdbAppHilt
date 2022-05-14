package com.mendelin.tmdb_hilt.domain.models.rest_api

import androidx.annotation.Keep

@Keep
data class CreatedByItem(
    val id: Int,
    val credit_id: String,
    val name: String,
    val gender: Int,
    val profile_path: String?
)