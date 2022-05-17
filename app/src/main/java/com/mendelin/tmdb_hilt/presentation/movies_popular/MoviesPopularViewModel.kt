package com.mendelin.tmdb_hilt.presentation.movies_popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mendelin.tmdb_hilt.base.BaseViewModel
import com.mendelin.tmdb_hilt.common.Utils.setFavoriteMovies
import com.mendelin.tmdb_hilt.data.repository.local.FavoritesRepository
import com.mendelin.tmdb_hilt.data.repository.local.PreferencesRepository
import com.mendelin.tmdb_hilt.data.repository.remote.MoviesRepository
import com.mendelin.tmdb_hilt.domain.models.entity.MovieListResultEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesPopularViewModel @Inject constructor(private val favorites: FavoritesRepository, private val repo: MoviesRepository, private val preferences: PreferencesRepository) : BaseViewModel() {
    val pagingData = Pager(
        config = PagingConfig(pageSize = PreferencesRepository.ITEMS_PER_PAGE),
        pagingSourceFactory = {
            MoviesPopularPagingSource(repo, preferences)
        }
    ).flow.cachedIn(viewModelScope)

    val popularMovies = MutableLiveData<PagingData<MovieListResultEntity>>()

    fun fetchPopularMovies() {
        viewModelScope.launch {
            pagingData.collectLatest { data ->
                popularMovies.postValue(data.setFavoriteMovies(favorites))
            }
        }
    }
}