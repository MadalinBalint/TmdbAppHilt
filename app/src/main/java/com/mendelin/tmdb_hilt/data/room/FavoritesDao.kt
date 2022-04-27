package com.mendelin.tmdb_hilt.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mendelin.tmdb_hilt.data.model.entity.MovieListResultItem
import com.mendelin.tmdb_hilt.data.model.entity.TvListResultItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    /* Movies */
    @Insert(onConflict = REPLACE)
    suspend fun insertFavoriteMovie(movie: MovieListResultItem)

    @Query("DELETE FROM favorite_movies WHERE id = :id")
    suspend fun deleteFavoriteMovie(id: Int)

    @Query("SELECT * FROM favorite_movies WHERE id = :id LIMIT 1")
    suspend fun isFavoriteMovie(id: Int): MovieListResultItem?

    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies(): Flow<List<MovieListResultItem>>

    /* TV Shows */
    @Insert(onConflict = REPLACE)
    suspend fun insertFavoriteTvShow(tvShow: TvListResultItem)

    @Query("DELETE FROM favorite_tvshows WHERE id = :id")
    suspend fun deleteFavoriteTvShow(id: Int)

    @Query("SELECT * FROM favorite_tvshows WHERE id = :id LIMIT 1")
    suspend fun isFavoriteTvShow(id: Int): TvListResultItem?

    @Query("SELECT * FROM favorite_tvshows")
    fun getFavoriteTvShows(): Flow<List<TvListResultItem>>
}