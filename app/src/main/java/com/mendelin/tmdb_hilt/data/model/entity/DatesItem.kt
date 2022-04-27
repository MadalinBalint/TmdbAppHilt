package com.mendelin.tmdb_hilt.data.model.entity

import androidx.annotation.Keep
import java.util.*

@Keep
data class DatesItem(
    val maximum: Date,
    val minimum: Date
)