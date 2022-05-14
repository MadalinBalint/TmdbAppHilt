package com.mendelin.tmdb_hilt.presentation.tv_show_season_episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mendelin.tmdb_hilt.base.BaseViewModel
import com.mendelin.tmdb_hilt.common.RetrofitResponseHandler
import com.mendelin.tmdb_hilt.domain.models.rest_api.EpisodeItem
import com.mendelin.tmdb_hilt.domain.models.response.TvSeasonDetailsResponse
import com.mendelin.tmdb_hilt.data.repository.remote.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowSeasonEpisodesViewModel @Inject constructor(private val repo: TvShowsRepository) : BaseViewModel() {
    private val seasonDetails = MutableLiveData<TvSeasonDetailsResponse>()
    private val episodeList = MutableLiveData<List<EpisodeItem>>()

    fun fetchTvShowDetails(tv_id: Int, season: Int) {
        isLoading.value = true
        RetrofitResponseHandler<TvSeasonDetailsResponse>(
            { response ->
                seasonDetails.postValue(response)
                episodeList.postValue(response.episodes)
                isLoading.value = false
            },
            { errorMsg ->
                error.postValue(errorMsg)
                isLoading.value = false
            })
            .processSeasonData(tv_id, season, repo::getTvSeasonDetails)

    }

    val details: LiveData<TvSeasonDetailsResponse>
        get() = seasonDetails

    val seasons: LiveData<List<EpisodeItem>>
        get() = episodeList
}