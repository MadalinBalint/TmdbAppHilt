package com.mendelin.tmdb_hilt.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mendelin.tmdb_hilt.domain.models.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.domain.models.entity.TvListResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    /* Movies */
    @Insert(onConflict = REPLACE)
    suspend fun insertFavoriteMovie(movie: MovieListResultEntity)

    @Query("DELETE FROM favorite_movies WHERE id = :id")
    suspend fun deleteFavoriteMovie(id: Int)

    @Query("SELECT * FROM favorite_movies WHERE id = :id LIMIT 1")
    suspend fun isFavoriteMovie(id: Int): MovieListResultEntity?

    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies(): Flow<List<MovieListResultEntity>>

    /* TV Shows */
    @Insert(onConflict = REPLACE)
    suspend fun insertFavoriteTvShow(tvShow: TvListResultEntity)

    @Query("DELETE FROM favorite_tvshows WHERE id = :id")
    suspend fun deleteFavoriteTvShow(id: Int)

    @Query("SELECT * FROM favorite_tvshows WHERE id = :id LIMIT 1")
    suspend fun isFavoriteTvShow(id: Int): TvListResultEntity?

    @Query("SELECT * FROM favorite_tvshows")
    fun getFavoriteTvShows(): Flow<List<TvListResultEntity>>
}