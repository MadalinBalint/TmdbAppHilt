package com.mendelin.tmdb_hilt.ui.tv_show_seasons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mendelin.tmdb_hilt.base.BaseViewModel
import com.mendelin.tmdb_hilt.common.RetrofitResponseHandler
import com.mendelin.tmdb_hilt.data.model.entity.SeasonItem
import com.mendelin.tmdb_hilt.data.model.response.CreditsResponse
import com.mendelin.tmdb_hilt.data.model.response.TvShowDetailsResponse
import com.mendelin.tmdb_hilt.data.repository.remote.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowSeasonsViewModel @Inject constructor(private val repo: TvShowsRepository) : BaseViewModel() {
    private val tvDetails = MutableLiveData<TvShowDetailsResponse>()
    private val tvCredits = MutableLiveData<CreditsResponse>()
    private val tvSeasons = MutableLiveData<List<SeasonItem>>()

    fun fetchTvShowDetails(tv_id: Int) {
        RetrofitResponseHandler<TvShowDetailsResponse>(
            { details ->
                tvDetails.postValue(details)
                tvSeasons.postValue(details.seasons)
                isLoading.value = false
            },
            { errorMsg ->
                error.postValue(errorMsg)
                isLoading.value = false
            })
            .processData(tv_id, repo::getTvShowDetails)
    }

    fun fetchTvShowCredits(tv_id: Int) {
        RetrofitResponseHandler<CreditsResponse>(
            { credits ->
                tvCredits.postValue(credits)
                isLoading.value = false
            },
            { errorMsg ->
                error.postValue(errorMsg)
                isLoading.value = false
            })
            .processData(tv_id, repo::getTvShowCredits)
    }

    val details: LiveData<TvShowDetailsResponse>
        get() = tvDetails

    val credits: LiveData<CreditsResponse>
        get() = tvCredits

    val seasons: LiveData<List<SeasonItem>>
        get() = tvSeasons
}