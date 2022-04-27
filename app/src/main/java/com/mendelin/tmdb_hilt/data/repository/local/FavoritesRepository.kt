package com.mendelin.tmdb_hilt.data.repository.local

import com.mendelin.tmdb_hilt.data.model.entity.MovieListResultItem
import com.mendelin.tmdb_hilt.data.model.entity.TvListResultItem
import com.mendelin.tmdb_hilt.data.room.FavoritesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class FavoritesRepository @Inject constructor(val dataSource: FavoritesDao) {
    /* Movies */
    suspend fun insertFavoriteMovie(movie: MovieListResultItem) {
        dataSource.insertFavoriteMovie(movie)
    }

    fun isFavoriteMovie(id: Int): Boolean {
        var favorite: Boolean
        runBlocking(Dispatchers.IO) {
            favorite = dataSource.isFavoriteMovie(id) != null
        }
        return favorite
    }

    suspend fun deleteFavoriteMovie(id: Int) =
        dataSource.deleteFavoriteMovie(id)

    fun getFavoriteMovies(): Flow<List<MovieListResultItem>> =
        dataSource.getFavoriteMovies()

    /* TV Shows */
    suspend fun insertFavoriteTvShow(tvShow: TvListResultItem) {
        dataSource.insertFavoriteTvShow(tvShow)
    }

    fun isFavoriteTvShow(id: Int): Boolean {
        var favorite: Boolean
        runBlocking(Dispatchers.IO) {
            favorite = dataSource.isFavoriteTvShow(id) != null
        }
        return favorite
    }

    suspend fun deleteFavoriteTvShow(id: Int) =
        dataSource.deleteFavoriteTvShow(id)

    fun getFavoriteTvShows(): Flow<List<TvListResultItem>> =
        dataSource.getFavoriteTvShows()
}