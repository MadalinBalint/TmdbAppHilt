package com.mendelin.tmdb_hilt.presentation.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mendelin.tmdb_hilt.base.BaseViewModel
import com.mendelin.tmdb_hilt.data.repository.local.PreferencesRepository
import com.mendelin.tmdb_hilt.data.repository.remote.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repo: MoviesRepository, val preferences: PreferencesRepository) : BaseViewModel() {
    val topRatedMovies = Pager(
        config = PagingConfig(pageSize = PreferencesRepository.ITEMS_PER_PAGE),
        pagingSourceFactory = {
            HomePagingSource(repo, preferences)
        }
    ).flow.cachedIn(viewModelScope)
}