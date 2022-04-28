package com.mendelin.tmdb_hilt.ui.favorites

import androidx.lifecycle.MutableLiveData
import com.mendelin.tmdb_hilt.base.BaseViewModel
import com.mendelin.tmdb_hilt.common.FavoriteType
import com.mendelin.tmdb_hilt.data.model.entity.MultipleItem
import com.mendelin.tmdb_hilt.data.repository.local.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(val repository: FavoritesRepository) : BaseViewModel() {
    val favoritesList = MutableLiveData<List<MultipleItem>>()

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

            favoritesList.postValue(favorites)
        }
    }
}