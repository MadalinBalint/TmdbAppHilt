package com.mendelin.tmdb_hilt.presentation.tv_shows_top_rated

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mendelin.tmdb_hilt.domain.models.entity.TvListResultEntity
import com.mendelin.tmdb_hilt.data.repository.local.PreferencesRepository
import com.mendelin.tmdb_hilt.data.repository.remote.TvShowsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TvTopRatedPagingSource @Inject constructor(val repo: TvShowsRepository, val preferences: PreferencesRepository) : PagingSource<Int, TvListResultEntity>() {
    private var firstLoad = true

    override fun getRefreshKey(state: PagingState<Int, TvListResultEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvListResultEntity> {
        return try {
            val currentPage = if (firstLoad) {
                /* Load last viewed page from Datastore */
                firstLoad = false
                preferences.getPostion(PreferencesRepository.KEY_TV_TOP_RATED, 0).first() / PreferencesRepository.ITEMS_PER_PAGE + 1
            } else
                params.key!!

            val apiResponse = repo.getTopRatedTvShows(currentPage)
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