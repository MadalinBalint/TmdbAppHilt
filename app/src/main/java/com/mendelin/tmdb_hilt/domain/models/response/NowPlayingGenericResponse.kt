package com.mendelin.tmdb_hilt.domain.models.response

import androidx.annotation.Keep
import com.mendelin.tmdb_hilt.domain.models.rest_api.DatesItem

@Keep
data class NowPlayingGenericResponse<T>(
    val page: Int,
    val results: List<T> = emptyList(),
    val dates: DatesItem,
    val total_results: Int = 0,
    val total_pages: Int = 0,
    val status_message: String = "",
    val status_code: Int = 200
)