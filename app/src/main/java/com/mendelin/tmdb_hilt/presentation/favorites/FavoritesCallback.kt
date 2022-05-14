package com.mendelin.tmdb_hilt.presentation.favorites

import com.mendelin.tmdb_hilt.domain.models.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.domain.models.entity.TvListResultEntity

interface FavoritesCallback {
    suspend fun insertFavoriteMovie(movie: MovieListResultEntity)
    fun isFavoriteMovie(id: Int): Boolean
    suspend fun deleteFavoriteMovie(id: Int)

    suspend fun insertFavoriteTvShow(tvShow: TvListResultEntity)
    fun isFavoriteTvShow(id: Int): Boolean
    suspend fun deleteFavoriteTvShow(id: Int)

    fun fetchFavoritesList()
}