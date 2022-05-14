package com.mendelin.tmdb_hilt.data.repository.remote

import com.mendelin.tmdb_hilt.data.TmdbDataSource
import com.mendelin.tmdb_hilt.domain.models.response.PersonDetailsResponse
import com.mendelin.tmdb_hilt.domain.models.response.PersonMovieCreditsResponse
import com.mendelin.tmdb_hilt.domain.models.response.PersonTvCreditsResponse
import retrofit2.Response
import javax.inject.Inject

class PeopleRepository @Inject constructor(private val service: TmdbDataSource) {
    suspend fun getPersonDetails(person_id: Int): Response<PersonDetailsResponse> =
        service.getPersonDetails(person_id)

    suspend fun getPersonMovieCredits(person_id: Int): Response<PersonMovieCreditsResponse> =
        service.getPersonMovieCredits(person_id)

    suspend fun getPersonTvCredits(person_id: Int): Response<PersonTvCreditsResponse> =
        service.getPersonTvCredits(person_id)
}