package com.mendelin.tmdb_hilt.presentation.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mendelin.tmdb_hilt.base.BaseViewModel
import com.mendelin.tmdb_hilt.common.RetrofitResponseHandler
import com.mendelin.tmdb_hilt.domain.models.response.PersonDetailsResponse
import com.mendelin.tmdb_hilt.domain.models.response.PersonMovieCreditsResponse
import com.mendelin.tmdb_hilt.domain.models.response.PersonTvCreditsResponse
import com.mendelin.tmdb_hilt.data.repository.remote.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val repo: PeopleRepository) : BaseViewModel() {
    private val personDetails = MutableLiveData<PersonDetailsResponse>()
    private val personMovieCredits = MutableLiveData<PersonMovieCreditsResponse>()
    private val personTvCredits = MutableLiveData<PersonTvCreditsResponse>()

    fun fetchPersonDetails(person_id: Int) {
        isLoading.value = true
        RetrofitResponseHandler<PersonDetailsResponse>(
            { response ->
                personDetails.postValue(response)
                isLoading.value = false
            },
            { errorMsg ->
                error.postValue(errorMsg)
                isLoading.value = false
            })
            .processData(person_id, repo::getPersonDetails)
    }

    fun fetchMovieCredits(person_id: Int) {
        isLoading.value = true
        RetrofitResponseHandler<PersonMovieCreditsResponse>(
            { response ->
                personMovieCredits.postValue(response)
                isLoading.value = false
            },

            { errorMsg ->
                error.postValue(errorMsg)
                isLoading.value = false
            })
            .processData(person_id, repo::getPersonMovieCredits)
    }

    fun fetchTvCredits(person_id: Int) {
        isLoading.value = true
        RetrofitResponseHandler<PersonTvCreditsResponse>(
            { response ->
                personTvCredits.postValue(response)
                isLoading.value = false
            },

            { errorMsg ->
                error.postValue(errorMsg)
                isLoading.value = false
            })
            .processData(person_id, repo::getPersonTvCredits)
    }

    val details: LiveData<PersonDetailsResponse>
        get() = personDetails

    val movieCredits: LiveData<PersonMovieCreditsResponse>
        get() = personMovieCredits

    val tvCredits: LiveData<PersonTvCreditsResponse>
        get() = personTvCredits
}