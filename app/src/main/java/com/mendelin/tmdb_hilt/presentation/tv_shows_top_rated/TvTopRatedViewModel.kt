package com.mendelin.tmdb_hilt.presentation.tv_shows_top_rated

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mendelin.tmdb_hilt.base.BaseViewModel
import com.mendelin.tmdb_hilt.common.Utils.setFavoriteTvShows
import com.mendelin.tmdb_hilt.data.repository.local.FavoritesRepository
import com.mendelin.tmdb_hilt.data.repository.local.PreferencesRepository
import com.mendelin.tmdb_hilt.data.repository.remote.TvShowsRepository
import com.mendelin.tmdb_hilt.domain.models.entity.TvListResultEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvTopRatedViewModel @Inject constructor(private val favorites: FavoritesRepository, private val repository: TvShowsRepository, private val preferences: PreferencesRepository) : BaseViewModel() {
    private val pagingData = Pager(
        config = PagingConfig(pageSize = PreferencesRepository.ITEMS_PER_PAGE),
        pagingSourceFactory = {
            TvTopRatedPagingSource(repository, preferences)
        }
    ).flow.cachedIn(viewModelScope)

    val topRatedTvShows = MutableLiveData<PagingData<TvListResultEntity>>()

    fun fetchTopRatedTvShows() {
        viewModelScope.launch {
            pagingData.collectLatest { data ->
                topRatedTvShows.postValue(data.setFavoriteTvShows(favorites))
            }
        }
    }
}