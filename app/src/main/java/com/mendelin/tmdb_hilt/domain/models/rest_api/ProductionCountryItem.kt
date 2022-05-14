package com.mendelin.tmdb_hilt.domain.models.rest_api

import androidx.annotation.Keep

@Keep
data class ProductionCountryItem(val iso_3166_1: String, val name: String)