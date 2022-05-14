package com.mendelin.tmdb_hilt.data.repository.remote

import com.mendelin.tmdb_hilt.data.TmdbDataSource
import com.mendelin.tmdb_hilt.domain.models.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.domain.models.response.CreditsResponse
import com.mendelin.tmdb_hilt.domain.models.response.MovieDetailsResponse
import com.mendelin.tmdb_hilt.domain.models.response.NowPlayingGenericResponse
import com.mendelin.tmdb_hilt.domain.models.response.PagedGenericResponse
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val service: TmdbDataSource) {
    suspend fun getMovieDetails(movie_id: Int): Response<MovieDetailsResponse> =
        service.getMovieDetails(movie_id)

    suspend fun getMovieCredits(movie_id: Int): Response<CreditsResponse> =
        service.getMovieCredits(movie_id)

    suspend fun getNowPlayingMovies(page: Int): Response<NowPlayingGenericResponse<MovieListResultEntity>> =
        service.getMovieNowPlaying(page = page)

    suspend fun getPopularMovies(page: Int): Response<PagedGenericResponse<MovieListResultEntity>> =
        service.getMoviePopular(page = page)

    suspend fun getTopRatedMovies(page: Int): Response<PagedGenericResponse<MovieListResultEntity>> =
        service.getMoviesTopRated(page = page)

    suspend fun getUpcomingMovies(page: Int): Response<NowPlayingGenericResponse<MovieListResultEntity>> =
        service.getMovieUpcoming(page = page)
}