package com.mendelin.tmdb_hilt.domain.models.entity

import androidx.annotation.Keep

@Keep
sealed class FavoriteEntity {
    var isFavorite: Boolean = false
}
