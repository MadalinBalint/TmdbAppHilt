package com.mendelin.tmdb_hilt.data.repository.local

import com.mendelin.tmdb_hilt.data.room.FavoritesDao
import com.mendelin.tmdb_hilt.domain.models.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.domain.models.entity.TvListResultEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepository @Inject constructor(private val dataSource: FavoritesDao) {
    /* Movies */
    suspend fun insertFavoriteMovie(movie: MovieListResultEntity) =
        dataSource.insertFavoriteMovie(movie)

    suspend fun isFavoriteMovie(id: Int): Boolean =
        dataSource.isFavoriteMovie(id) != null

    suspend fun deleteFavoriteMovie(id: Int) =
        dataSource.deleteFavoriteMovie(id)

    fun getFavoriteMovies(): Flow<List<MovieListResultEntity>> =
        dataSource.getFavoriteMovies()

    /* TV Shows */
    suspend fun insertFavoriteTvShow(tvShow: TvListResultEntity) =
        dataSource.insertFavoriteTvShow(tvShow)


    suspend fun isFavoriteTvShow(id: Int): Boolean =
        dataSource.isFavoriteTvShow(id) != null

    suspend fun deleteFavoriteTvShow(id: Int) =
        dataSource.deleteFavoriteTvShow(id)

    fun getFavoriteTvShows(): Flow<List<TvListResultEntity>> =
        dataSource.getFavoriteTvShows()
}