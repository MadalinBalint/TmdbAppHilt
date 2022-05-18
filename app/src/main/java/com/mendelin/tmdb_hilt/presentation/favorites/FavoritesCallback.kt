package com.mendelin.tmdb_hilt.presentation.favorites

import com.mendelin.tmdb_hilt.domain.models.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.domain.models.entity.TvListResultEntity

interface FavoritesCallback {
    fun insertFavoriteMovie(movie: MovieListResultEntity)
    fun deleteFavoriteMovie(id: Int)

    fun insertFavoriteTvShow(tvShow: TvListResultEntity)
    fun deleteFavoriteTvShow(id: Int)

    fun getFavoritesList()
}