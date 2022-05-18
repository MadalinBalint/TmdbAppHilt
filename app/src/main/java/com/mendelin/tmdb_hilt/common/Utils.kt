package com.mendelin.tmdb_hilt.common

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import com.mendelin.tmdb_hilt.base.BaseViewModel
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

    fun FavoritesViewModel.getFavoritesCallback(): FavoritesCallback {
        return object : FavoritesCallback {
            override fun insertFavoriteMovie(movie: MovieListResultEntity) {
                addFavoriteMovie(movie)
            }

            override fun deleteFavoriteMovie(id: Int) {
                removeFavoriteMovie(id)
            }

            override fun insertFavoriteTvShow(tvShow: TvListResultEntity) {
                addFavoriteTvShow(tvShow)
            }

            override fun deleteFavoriteTvShow(id: Int) {
                removeFavoriteTvShow(id)
            }

            override fun getFavoritesList() {
                fetchFavoritesList()
            }
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

    fun BaseViewModel.setUiState(state: CombinedLoadStates) {
        isLoading.value = state.refresh is LoadState.Loading

        val errorState = state.refresh as? LoadState.Error
            ?: state.source.append as? LoadState.Error
            ?: state.source.prepend as? LoadState.Error
            ?: state.append as? LoadState.Error
            ?: state.prepend as? LoadState.Error

        errorState?.let {
            error.value = it.error.message
        }
    }
}