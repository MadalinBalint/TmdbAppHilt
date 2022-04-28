package com.mendelin.tmdb_hilt.data.repository.remote

import com.mendelin.tmdb_hilt.data.api.TmdbDataSource
import com.mendelin.tmdb_hilt.data.model.entity.TvListResultEntity
import com.mendelin.tmdb_hilt.data.model.response.*
import retrofit2.Response
import javax.inject.Inject

class TvShowsRepository @Inject constructor(val service: TmdbDataSource) {
    suspend fun getTvShowDetails(tv_id: Int): Response<TvShowDetailsResponse> =
        service.getTvDetails(tv_id)

    suspend fun getTvShowCredits(tv_id: Int): Response<CreditsResponse> =
        service.getTvCredits(tv_id)

    suspend fun getPopularTvShows(page: Int): Response<PagedGenericResponse<TvListResultEntity>> =
        service.getTvPopular(page = page)

    suspend fun getOnTheAirTvShows(page: Int): Response<PagedGenericResponse<TvListResultEntity>> =
        service.getTvOnTheAir(page = page)

    suspend fun getTopRatedTvShows(page: Int): Response<PagedGenericResponse<TvListResultEntity>> =
        service.getTvTopRated(page = page)

    suspend fun getTvSeasonDetails(tv_id: Int, season: Int): Response<TvSeasonDetailsResponse> =
        service.getTvSeasonDetails(tv_id, season)

    suspend fun getTvEpisodeDetails(tv_id: Int, season: Int, episode: Int): Response<TvEpisodeDetailsResponse> =
        service.getTvEpisodeDetails(tv_id, season, episode)
}