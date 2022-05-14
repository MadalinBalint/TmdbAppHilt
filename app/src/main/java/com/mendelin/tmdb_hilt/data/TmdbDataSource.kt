package com.mendelin.tmdb_hilt.data

import com.mendelin.tmdb_hilt.BuildConfig
import com.mendelin.tmdb_hilt.domain.models.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.domain.models.entity.TvListResultEntity
import com.mendelin.tmdb_hilt.domain.models.response.*
import com.mendelin.tmdb_hilt.domain.models.rest_api.GenreItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbDataSource {
    /* Genre */
    @GET(BuildConfig.ENDPOINT_MOVIE_GENRE_LIST)
    suspend fun getGenreMovie(): Response<GenericResponse<GenreItem>>

    @GET(BuildConfig.ENDPOINT_TV_GENRE_LIST)
    suspend fun getGenreTv(): Response<GenericResponse<GenreItem>>

    /* Movie */
    @GET(BuildConfig.ENDPOINT_MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path(BuildConfig.PATH_MOVIE_ID) movie_id: Int,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<MovieDetailsResponse>

    @GET(BuildConfig.ENDPOINT_MOVIE_CREDITS)
    suspend fun getMovieCredits(
        @Path(BuildConfig.PATH_MOVIE_ID) movie_id: Int,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<CreditsResponse>

    @GET(BuildConfig.ENDPOINT_MOVIE_NOW_PLAYING)
    suspend fun getMovieNowPlaying(
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1
    ): Response<NowPlayingGenericResponse<MovieListResultEntity>>

    @GET(BuildConfig.ENDPOINT_MOVIE_POPULAR)
    suspend fun getMoviePopular(
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1
    ): Response<PagedGenericResponse<MovieListResultEntity>>

    @GET(BuildConfig.ENDPOINT_MOVIE_TOP_RATED)
    suspend fun getMoviesTopRated(
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1
    ): Response<PagedGenericResponse<MovieListResultEntity>>

    @GET(BuildConfig.ENDPOINT_MOVIE_UPCOMING)
    suspend fun getMovieUpcoming(
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US"
    ): Response<NowPlayingGenericResponse<MovieListResultEntity>>

    /* People */
    @GET(BuildConfig.ENDPOINT_PERSON_DETAILS)
    suspend fun getPersonDetails(
        @Path(BuildConfig.PATH_PERSON_ID) person_id: Int,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<PersonDetailsResponse>

    @GET(BuildConfig.ENDPOINT_PERSON_MOVIE_CREDITS)
    suspend fun getPersonMovieCredits(
        @Path(BuildConfig.PATH_PERSON_ID) person_id: Int,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<PersonMovieCreditsResponse>

    @GET(BuildConfig.ENDPOINT_PERSON_TV_CREDITS)
    suspend fun getPersonTvCredits(
        @Path(BuildConfig.PATH_PERSON_ID) person_id: Int,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<PersonTvCreditsResponse>

    /* TV shows */
    @GET(BuildConfig.ENDPOINT_TV_DETAILS)
    suspend fun getTvDetails(
        @Path(BuildConfig.PATH_TV_ID) tv_id: Int,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<TvShowDetailsResponse>

    @GET(BuildConfig.ENDPOINT_TV_CREDITS)
    suspend fun getTvCredits(
        @Path(BuildConfig.PATH_TV_ID) tv_id: Int,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<CreditsResponse>

    @GET(BuildConfig.ENDPOINT_TV_ON_THE_AIR)
    suspend fun getTvOnTheAir(
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<PagedGenericResponse<TvListResultEntity>>

    @GET(BuildConfig.ENDPOINT_TV_POPULAR)
    suspend fun getTvPopular(
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1
    ): Response<PagedGenericResponse<TvListResultEntity>>

    @GET(BuildConfig.ENDPOINT_TV_TOP_RATED)
    suspend fun getTvTopRated(
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<PagedGenericResponse<TvListResultEntity>>

    /* TV Seasons */
    @GET(BuildConfig.ENDPOINT_TV_SEASON_DETAILS)
    suspend fun getTvSeasonDetails(
        @Path(BuildConfig.PATH_TV_ID) tv_id: Int,
        @Path(BuildConfig.PATH_SEASON_NUMBER) season: Int,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<TvSeasonDetailsResponse>

    /* TV Episodes */
    @GET(BuildConfig.ENDPOINT_TV_EPISODE_DETAILS)
    suspend fun getTvEpisodeDetails(
        @Path(BuildConfig.PATH_TV_ID) tv_id: Int,
        @Path(BuildConfig.PATH_SEASON_NUMBER) season: Int,
        @Path(BuildConfig.PATH_EPISODE_NUMBER) episode: Int,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<TvEpisodeDetailsResponse>
}