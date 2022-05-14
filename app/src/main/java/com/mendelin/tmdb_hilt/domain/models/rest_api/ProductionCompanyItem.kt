package com.mendelin.tmdb_hilt.domain.models.rest_api

import androidx.annotation.Keep

@Keep
data class ProductionCompanyItem(
    val name: String,
    val id: Int,
    val logo_path: String?,
    val origin_country: String
)