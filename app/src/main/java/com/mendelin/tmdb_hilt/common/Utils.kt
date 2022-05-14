package com.mendelin.tmdb_hilt.common

import androidx.paging.PagingData
import androidx.paging.map
import com.mendelin.tmdb_hilt.data.repository.local.FavoritesRepository
import com.mendelin.tmdb_hilt.domain.models.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.domain.models.entity.TvListResultEntity
import com.mendelin.tmdb_hilt.presentation.favorites.FavoritesCallback
import com.mendelin.tmdb_hilt.presentation.favorites.FavoritesViewModel
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun String.getYear(): String {
        val sd = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sd.parse(this)
        val calendar = Calendar.getInstance()
        calendar.time = date!!
        return calendar.get(Calendar.YEAR).toString()
    }

    fun getFavoritesCallback(viewModel: FavoritesViewModel): FavoritesCallback =
        object : FavoritesCallback {
            override suspend fun insertFavoriteMovie(movie: MovieListResultEntity) {
                viewModel.repository.insertFavoriteMovie(movie)
            }

            override suspend fun deleteFavoriteMovie(id: Int) {
                viewModel.repository.deleteFavoriteMovie(id)
            }

            override suspend fun insertFavoriteTvShow(tvShow: TvListResultEntity) {
                viewModel.repository.insertFavoriteTvShow(tvShow)
            }

            override suspend fun deleteFavoriteTvShow(id: Int) {
                viewModel.repository.deleteFavoriteTvShow(id)
            }

            override fun fetchFavoritesList() {
                viewModel.fetchFavoritesList()
            }
        }

    fun PagingData<MovieListResultEntity>.setFavoriteMovies(repository: FavoritesRepository): PagingData<MovieListResultEntity> {
        return this.map {
            it.isFavorite = repository.isFavoriteMovie(it.id)
            it
        }
    }

    fun PagingData<TvListResultEntity>.setFavoriteTvShows(repository: FavoritesRepository): PagingData<TvListResultEntity> {
        return this.map {
            it.isFavorite = repository.isFavoriteTvShow(it.id)
            it
        }
    }
}