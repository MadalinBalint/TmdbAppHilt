package com.mendelin.tmdb_hilt.domain.models.rest_api

import androidx.annotation.Keep
import java.util.*

@Keep
data class DatesItem(
    val maximum: Date,
    val minimum: Date
)