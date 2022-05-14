package com.mendelin.tmdb_hilt.domain.models.response

import androidx.annotation.Keep
import com.mendelin.tmdb_hilt.domain.models.rest_api.MovieCreditsCastItem
import com.mendelin.tmdb_hilt.domain.models.rest_api.MovieCreditsCrewItem

@Keep
data class PersonMovieCreditsResponse(
    val cast: List<MovieCreditsCastItem>,
    val crew: List<MovieCreditsCrewItem>,
    val id: Int
)