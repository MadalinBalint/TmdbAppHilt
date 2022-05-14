package com.mendelin.tmdb_hilt.presentation.movies_now_playing

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mendelin.tmdb_hilt.domain.models.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.data.repository.local.PreferencesRepository
import com.mendelin.tmdb_hilt.data.repository.remote.MoviesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MoviesNowPlayingPagingSource @Inject constructor(private val repo: MoviesRepository, private val preferences: PreferencesRepository) : PagingSource<Int, MovieListResultEntity>() {
    private var firstLoad = true

    override fun getRefreshKey(state: PagingState<Int, MovieListResultEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieListResultEntity> {
        return try {
            val currentPage = if (firstLoad) {
                /* Load last viewed page from Datastore */
                firstLoad = false
                preferences.getPostion(PreferencesRepository.KEY_MOVIES_NOW_PLAYING, 0).first() / PreferencesRepository.ITEMS_PER_PAGE + 1
            } else
                params.key!!

            val apiResponse = repo.getNowPlayingMovies(currentPage)
            if (apiResponse.isSuccessful) {
                val response = apiResponse.body()
                if (response != null) {
                    LoadResult.Page(
                        data = response.results,
                        prevKey = if (currentPage > 1) currentPage - 1 else null,
                        nextKey = if (currentPage < response.total_pages) currentPage + 1 else null
                    )
                } else
                    LoadResult.Error(Exception("Null body response"))
            } else
                LoadResult.Error(Exception(apiResponse.message()))
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}