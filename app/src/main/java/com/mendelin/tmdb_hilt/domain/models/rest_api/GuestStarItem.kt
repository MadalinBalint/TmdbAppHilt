package com.mendelin.tmdb_hilt.domain.models.rest_api

import androidx.annotation.Keep

@Keep
data class GuestStarItem(
    val id: Int,
    val name: String,
    val credit_id: String,
    val character: String,
    val order: Int,
    val profile_path: String?,
)