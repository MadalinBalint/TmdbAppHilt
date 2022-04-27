package com.mendelin.tmdb_hilt.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mendelin.tmdb_hilt.data.model.entity.MovieListResultItem
import com.mendelin.tmdb_hilt.data.repository.local.PreferencesRepository
import com.mendelin.tmdb_hilt.data.repository.remote.MoviesRepository
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

class HomePagingSource @Inject constructor(val repo: MoviesRepository, val preferences: PreferencesRepository) : PagingSource<Int, MovieListResultItem>() {
    private var firstLoad = true

    override fun getRefreshKey(state: PagingState<Int, MovieListResultItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieListResultItem> {
        return try {
            val currentPage = if (firstLoad) {
                /* Load last viewed page from Datastore */
                firstLoad = false
                preferences.getPostion(PreferencesRepository.KEY_HOME, 0).first() / PreferencesRepository.ITEMS_PER_PAGE + 1
            } else
                params.key!!

            val apiResponse = repo.getTopRatedMovies(currentPage)
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
            Timber.e(exception.localizedMessage)
            return LoadResult.Error(exception)
        }
    }
}