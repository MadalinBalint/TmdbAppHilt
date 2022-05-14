package com.mendelin.tmdb_hilt.domain.models.response

import androidx.annotation.Keep
import com.mendelin.tmdb_hilt.domain.models.rest_api.TvCreditsCastItem
import com.mendelin.tmdb_hilt.domain.models.rest_api.TvCreditsCrewItem

@Keep
data class PersonTvCreditsResponse(
    val cast: List<TvCreditsCastItem>,
    val crew: List<TvCreditsCrewItem>,
    val id: Int
)