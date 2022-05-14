package com.mendelin.tmdb_hilt.domain.models.response

import androidx.annotation.Keep

@Keep
data class PagedGenericResponse<T>(
    val page: Int = 0,
    val results: List<T> = emptyList(),
    val total_results: Int = 0,
    val total_pages: Int = 0,
    val status_message: String = "",
    val success: Boolean,
    val status_code: Int = 200
)
