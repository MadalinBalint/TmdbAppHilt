package com.mendelin.tmdb_hilt.data.model.response

import androidx.annotation.Keep

@Keep
data class GenericResponse<T>(
    val results: List<T> = emptyList(),

    val status_message: String = "",
    val success: Boolean = true,
    val status_code: Int = 200
)