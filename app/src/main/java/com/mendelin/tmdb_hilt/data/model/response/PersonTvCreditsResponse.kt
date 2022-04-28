package com.mendelin.tmdb_hilt.data.model.response

import androidx.annotation.Keep
import com.mendelin.tmdb_hilt.data.model.rest_api.TvCreditsCastItem
import com.mendelin.tmdb_hilt.data.model.rest_api.TvCreditsCrewItem

@Keep
data class PersonTvCreditsResponse(
    val cast: List<TvCreditsCastItem>,
    val crew: List<TvCreditsCrewItem>,
    val id: Int
)