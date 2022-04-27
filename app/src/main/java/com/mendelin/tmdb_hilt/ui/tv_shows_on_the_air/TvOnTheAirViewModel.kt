package com.mendelin.tmdb_hilt.ui.tv_shows_on_the_air

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
class TvOnTheAirViewModel @Inject constructor(val repo: TvShowsRepository, val preferences: PreferencesRepository) : BaseViewModel() {
    val onTheAirTvShows = Pager(
        config = PagingConfig(pageSize = PreferencesRepository.ITEMS_PER_PAGE),
        pagingSourceFactory = {
            TvOnTheAirPagingSource(repo, preferences)
        }
    ).flow.cachedIn(viewModelScope)
}