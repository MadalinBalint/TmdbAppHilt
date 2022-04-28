package com.mendelin.tmdb_hilt.data.model.entity

import androidx.annotation.Keep
import com.mendelin.tmdb_hilt.common.FavoriteType

@Keep
data class MultipleItem(
    val type: FavoriteType,
    val content: FavoriteEntity
)