package com.mendelin.tmdb_hilt.ui.tv_shows_top_rated

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mendelin.tmdb_hilt.base.BaseViewModel
import com.mendelin.tmdb_hilt.data.repository.local.PreferencesRepository
import com.mendelin.tmdb_hilt.data.repository.remote.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvTopRatedViewModel @Inject constructor(val repo: TvShowsRepository, val preferences: PreferencesRepository) : BaseViewModel() {
    val topRatedTvShows = Pager(
        config = PagingConfig(pageSize = PreferencesRepository.ITEMS_PER_PAGE),
        pagingSourceFactory = {
            TvTopRatedPagingSource(repo, preferences)
        }
    ).flow.cachedIn(viewModelScope)
}