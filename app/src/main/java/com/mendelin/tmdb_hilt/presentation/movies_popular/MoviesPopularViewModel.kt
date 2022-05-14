package com.mendelin.tmdb_hilt.presentation.movies_popular

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
class MoviesPopularViewModel @Inject constructor(private val repo: MoviesRepository, private val preferences: PreferencesRepository) : BaseViewModel() {
    val popularMovies = Pager(
        config = PagingConfig(pageSize = PreferencesRepository.ITEMS_PER_PAGE),
        pagingSourceFactory = {
            MoviesPopularPagingSource(repo, preferences)
        }
    ).flow.cachedIn(viewModelScope)
}