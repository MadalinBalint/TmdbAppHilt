package com.mendelin.tmdb_hilt.presentation.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mendelin.tmdb_hilt.base.BaseViewModel
import com.mendelin.tmdb_hilt.data.repository.remote.MoviesRepository
import com.mendelin.tmdb_hilt.domain.models.response.CreditsResponse
import com.mendelin.tmdb_hilt.domain.models.response.MovieDetailsResponse
import com.mendelin.tmdb_hilt.domain.models.rest_api.CastItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MoviesDetailsViewModel @Inject constructor(private val repo: MoviesRepository) : BaseViewModel() {
    private val movieDetails = MutableLiveData<MovieDetailsResponse>()
    private val movieCredits = MutableLiveData<CreditsResponse>()
    private val movieCasting = MutableLiveData<List<CastItem>>()
    private val disposables = CompositeDisposable()

    fun fetchMovieDetails(movieId: Int) {
        isLoading.value = true

        disposables.add(
            repo.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        movieDetails.postValue(response)
                        isLoading.value = false
                    },
                    { errorMsg ->
                        error.postValue(errorMsg.localizedMessage)
                        isLoading.value = false
                    }
                )
        )
    }

    fun fetchMovieCredits(movieId: Int) {
        isLoading.value = true

        disposables.add(
            repo.getMovieCredits(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        movieCredits.postValue(response)
                        movieCasting.postValue(response.cast)

                        isLoading.value = false
                    },

                    { errorMsg ->
                        error.postValue(errorMsg.localizedMessage)
                        isLoading.value = false
                    }
                )
        )
    }

    val details: LiveData<MovieDetailsResponse>
        get() = movieDetails

    val credits: LiveData<CreditsResponse>
        get() = movieCredits

    val casting: LiveData<List<CastItem>>
        get() = movieCasting

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}