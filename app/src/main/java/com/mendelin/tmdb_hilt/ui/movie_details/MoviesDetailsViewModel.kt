package com.mendelin.tmdb_hilt.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mendelin.tmdb_hilt.base.BaseViewModel
import com.mendelin.tmdb_hilt.common.RetrofitResponseHandler
import com.mendelin.tmdb_hilt.data.model.rest_api.CastItem
import com.mendelin.tmdb_hilt.data.model.response.CreditsResponse
import com.mendelin.tmdb_hilt.data.model.response.MovieDetailsResponse
import com.mendelin.tmdb_hilt.data.repository.remote.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesDetailsViewModel @Inject constructor(private val repo: MoviesRepository) : BaseViewModel() {
    private val movieDetails = MutableLiveData<MovieDetailsResponse>()
    private val movieCredits = MutableLiveData<CreditsResponse>()
    private val movieCasting = MutableLiveData<List<CastItem>>()

    fun fetchMovieDetails(movie_id: Int) {
        isLoading.value = true
        RetrofitResponseHandler<MovieDetailsResponse>(
            { response ->
                movieDetails.postValue(response)
                isLoading.value = false
            },
            { errorMsg ->
                error.postValue(errorMsg)
                isLoading.value = false
            })
            .processData(movie_id, repo::getMovieDetails)
    }

    fun fetchMovieCredits(movie_id: Int) {
        isLoading.value = true
        RetrofitResponseHandler<CreditsResponse>(
            { response ->
                movieCredits.postValue(response)
                movieCasting.postValue(response.cast)

                isLoading.value = false
            },

            { errorMsg ->
                error.postValue(errorMsg)
                isLoading.value = false
            })
            .processData(movie_id, repo::getMovieCredits)
    }

    val details: LiveData<MovieDetailsResponse>
        get() = movieDetails

    val credits: LiveData<CreditsResponse>
        get() = movieCredits

    val casting: LiveData<List<CastItem>>
        get() = movieCasting
}