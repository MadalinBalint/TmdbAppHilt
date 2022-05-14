package com.mendelin.tmdb_hilt.domain.models.entity

import androidx.annotation.Keep
import com.mendelin.tmdb_hilt.common.FavoriteType

@Keep
data class MultipleItem(
    val type: FavoriteType,
    val content: FavoriteEntity
)