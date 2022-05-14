package com.mendelin.tmdb_hilt.presentation.movies_upcoming

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mendelin.tmdb_hilt.base.BaseViewModel
import com.mendelin.tmdb_hilt.data.repository.local.FavoritesRepository
import com.mendelin.tmdb_hilt.data.repository.local.PreferencesRepository
import com.mendelin.tmdb_hilt.data.repository.remote.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesUpcomingViewModel @Inject constructor(val favorites: FavoritesRepository, val repo: MoviesRepository, val preferences: PreferencesRepository) : BaseViewModel() {
    val upcomingMovies = Pager(
        config = PagingConfig(pageSize = PreferencesRepository.ITEMS_PER_PAGE),
        pagingSourceFactory = {
            MoviesUpcomingPagingSource(repo, preferences)
        }
    ).flow.cachedIn(viewModelScope)
}