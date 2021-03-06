package com.mendelin.tmdb_hilt.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mendelin.tmdb_hilt.base.BaseViewModel
import com.mendelin.tmdb_hilt.common.FavoriteType
import com.mendelin.tmdb_hilt.data.repository.local.FavoritesRepository
import com.mendelin.tmdb_hilt.domain.models.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.domain.models.entity.MultipleItem
import com.mendelin.tmdb_hilt.domain.models.entity.TvListResultEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: FavoritesRepository) : BaseViewModel() {
    private val favoritesList = MutableLiveData<List<MultipleItem>>()

    fun getFavoritesList(): LiveData<List<MultipleItem>> = favoritesList

    private suspend fun fetchFavoriteMovies(): List<MultipleItem> {
        return repository.getFavoriteMovies()
            .first()
            .map {
                MultipleItem(FavoriteType.FAVORITE_MOVIE, it)
            }
    }

    private suspend fun fetchFavoriteTvShows(): List<MultipleItem> {
        return repository.getFavoriteTvShows()
            .first()
            .map {
                MultipleItem(FavoriteType.FAVORITE_TV_SHOW, it)
            }
    }

    fun fetchFavoritesList() {
        CoroutineScope(Dispatchers.IO).launch {
            val favorites = mutableListOf<MultipleItem>()
            val movies = fetchFavoriteMovies()
            val tvShows = fetchFavoriteTvShows()

            Timber.d("We have ${movies.size} favorite movies")
            Timber.d("We have ${tvShows.size} favorite TV shows")

            favorites.addAll(movies)
            favorites.addAll(tvShows)

            favorites.forEach { item ->
                when (item.content) {
                    is MovieListResultEntity ->
                        item.content.isFavorite = repository.isFavoriteMovie(item.content.id)
                    is TvListResultEntity ->
                        item.content.isFavorite = repository.isFavoriteTvShow(item.content.id)
                }
            }

            favoritesList.postValue(favorites)
        }
    }

    fun addFavoriteMovie(movie: MovieListResultEntity) {
        viewModelScope.launch {
            repository.insertFavoriteMovie(movie)
        }
    }

    fun removeFavoriteMovie(id: Int) {
        viewModelScope.launch {
            repository.deleteFavoriteMovie(id)
        }
    }

    fun addFavoriteTvShow(tvShow: TvListResultEntity) {
        viewModelScope.launch {
            repository.insertFavoriteTvShow(tvShow)
        }
    }

    fun removeFavoriteTvShow(id: Int) {
        viewModelScope.launch {
            repository.deleteFavoriteTvShow(id)
        }
    }
}