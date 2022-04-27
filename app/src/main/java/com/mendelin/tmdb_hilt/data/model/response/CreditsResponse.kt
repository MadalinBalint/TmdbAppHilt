package com.mendelin.tmdb_hilt.data.model.response

import androidx.annotation.Keep
import com.mendelin.tmdb_hilt.data.model.entity.CastItem
import com.mendelin.tmdb_hilt.data.model.entity.CrewItem

@Keep
data class CreditsResponse(
    val id: Int,
    val cast: List<CastItem>,
    val crew: List<CrewItem>,

    val status_message: String = "",
    val success: Boolean = true,
    val status_code: Int = 200
) {
    private fun getDirector(): CrewItem? {
       return crew.firstOrNull { it.job == "Director" }
    }

    fun getDirectorName(): String {
        return getDirector()?.name ?: ""
    }
}