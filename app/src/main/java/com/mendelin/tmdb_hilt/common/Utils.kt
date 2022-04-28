package com.mendelin.tmdb_hilt.common

import com.mendelin.tmdb_hilt.data.model.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.data.model.entity.TvListResultEntity
import com.mendelin.tmdb_hilt.ui.favorites.FavoritesCallback
import com.mendelin.tmdb_hilt.ui.favorites.FavoritesViewModel
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

            override fun isFavoriteMovie(id: Int): Boolean {
                return viewModel.repository.isFavoriteMovie(id)
            }

            override suspend fun deleteFavoriteMovie(id: Int) {
                viewModel.repository.deleteFavoriteMovie(id)
            }

            override suspend fun insertFavoriteTvShow(tvShow: TvListResultEntity) {
                viewModel.repository.insertFavoriteTvShow(tvShow)
            }

            override fun isFavoriteTvShow(id: Int): Boolean {
                return viewModel.repository.isFavoriteTvShow(id)
            }

            override suspend fun deleteFavoriteTvShow(id: Int) {
                viewModel.repository.deleteFavoriteTvShow(id)
            }

            override fun fetchFavoritesList() {
                viewModel.fetchFavoritesList()
            }
        }
}