package com.mendelin.tmdb_hilt.data.api

import com.mendelin.tmdb_hilt.BuildConfig
import com.mendelin.tmdb_hilt.data.model.entity.GenreItem
import com.mendelin.tmdb_hilt.data.model.entity.MovieListResultItem
import com.mendelin.tmdb_hilt.data.model.entity.TvListResultItem
import com.mendelin.tmdb_hilt.data.model.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

interface TmdbDataSource {
    /* Genre */
    @GET(BuildConfig.ENDPOINT_MOVIE_GENRE_LIST)
    suspend fun getGenreMovie(
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY
    ): Response<GenericResponse<GenreItem>>

    @GET(BuildConfig.ENDPOINT_TV_GENRE_LIST)
    suspend fun getGenreTv(
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY
    ): Response<GenericResponse<GenreItem>>

    /* Movie */
    @GET(BuildConfig.ENDPOINT_MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path(BuildConfig.PATH_MOVIE_ID) movie_id: Int,
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<MovieDetailsResponse>

    @GET(BuildConfig.ENDPOINT_MOVIE_CREDITS)
    suspend fun getMovieCredits(
        @Path(BuildConfig.PATH_MOVIE_ID) movie_id: Int,
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<CreditsResponse>

    @GET(BuildConfig.ENDPOINT_MOVIE_NOW_PLAYING)
    suspend fun getMovieNowPlaying(
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1
    ): Response<NowPlayingGenericResponse<MovieListResultItem>>

    @GET(BuildConfig.ENDPOINT_MOVIE_POPULAR)
    suspend fun getMoviePopular(
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1
    ): Response<PagedGenericResponse<MovieListResultItem>>

    @GET(BuildConfig.ENDPOINT_MOVIE_TOP_RATED)
    suspend fun getMoviesTopRated(
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1
    ): Response<PagedGenericResponse<MovieListResultItem>>

    @GET(BuildConfig.ENDPOINT_MOVIE_UPCOMING)
    suspend fun getMovieUpcoming(
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1,
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US"
    ): Response<NowPlayingGenericResponse<MovieListResultItem>>

    /* People */
    @GET(BuildConfig.ENDPOINT_PERSON_DETAILS)
    suspend fun getPersonDetails(
        @Path(BuildConfig.PATH_PERSON_ID) person_id: Int,
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<PersonDetailsResponse>

    @GET(BuildConfig.ENDPOINT_PERSON_MOVIE_CREDITS)
    suspend fun getPersonMovieCredits(
        @Path(BuildConfig.PATH_PERSON_ID) person_id: Int,
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<PersonMovieCreditsResponse>

    @GET(BuildConfig.ENDPOINT_PERSON_TV_CREDITS)
    suspend fun getPersonTvCredits(
        @Path(BuildConfig.PATH_PERSON_ID) person_id: Int,
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<PersonTvCreditsResponse>

    /* TV shows */
    @GET(BuildConfig.ENDPOINT_TV_DETAILS)
    suspend fun getTvDetails(
        @Path(BuildConfig.PATH_TV_ID) tv_id: Int,
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<TvShowDetailsResponse>

    @GET(BuildConfig.ENDPOINT_TV_CREDITS)
    suspend fun getTvCredits(
        @Path(BuildConfig.PATH_TV_ID) tv_id: Int,
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<CreditsResponse>

    @GET(BuildConfig.ENDPOINT_TV_ON_THE_AIR)
    suspend fun getTvOnTheAir(
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1,
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<PagedGenericResponse<TvListResultItem>>

    @GET(BuildConfig.ENDPOINT_TV_POPULAR)
    suspend fun getTvPopular(
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1
    ): Response<PagedGenericResponse<TvListResultItem>>

    @GET(BuildConfig.ENDPOINT_TV_TOP_RATED)
    suspend fun getTvTopRated(
        @Query(BuildConfig.QUERY_PAGE) page: Int = 1,
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<PagedGenericResponse<TvListResultItem>>

    /* TV Seasons */
    @GET(BuildConfig.ENDPOINT_TV_SEASON_DETAILS)
    suspend fun getTvSeasonDetails(
        @Path(BuildConfig.PATH_TV_ID) tv_id: Int,
        @Path(BuildConfig.PATH_SEASON_NUMBER) season: Int,
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<TvSeasonDetailsResponse>

    /* TV Episodes */
    @GET(BuildConfig.ENDPOINT_TV_EPISODE_DETAILS)
    suspend fun getTvEpisodeDetails(
        @Path(BuildConfig.PATH_TV_ID) tv_id: Int,
        @Path(BuildConfig.PATH_SEASON_NUMBER) season: Int,
        @Path(BuildConfig.PATH_EPISODE_NUMBER) episode: Int,
        @Query(BuildConfig.QUERY_API_KEY) apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query(BuildConfig.QUERY_LANGUAGE) language: String = "en-US",
    ): Response<TvEpisodeDetailsResponse>
}