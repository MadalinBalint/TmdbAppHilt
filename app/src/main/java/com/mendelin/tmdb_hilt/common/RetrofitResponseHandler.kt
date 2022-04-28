package com.mendelin.tmdb_hilt.common

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

typealias OnSuccessCallback<T> = (response: T) -> Unit
typealias OnErrorCallback = (errorMsg: String) -> Unit

typealias RestApiCall<T> = suspend (id: Int) -> Response<T>
typealias RestApiSeasonCall<T> = suspend (id: Int, season: Int) -> Response<T>
typealias RestApiSeasonEpisodeCall<T> = suspend (id: Int, season: Int, episode: Int) -> Response<T>

class RetrofitResponseHandler<T>(val onSuccess: OnSuccessCallback<T>, val onError: OnErrorCallback) {
    fun processData(id: Int, restApiCall: RestApiCall<T>) {
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            onError("Coroutine exception ${ex.localizedMessage}")
        }

        CoroutineScope(Dispatchers.IO + exceptionHandler)
            .launch {
                val response = restApiCall(id)
                processApiResponse(response)
            }
    }

    fun processSeasonData(id: Int, season: Int, restApiCall: RestApiSeasonCall<T>) {
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            onError("Coroutine exception ${ex.localizedMessage}")
        }

        CoroutineScope(Dispatchers.IO + exceptionHandler)
            .launch {
                val response = restApiCall(id, season)
                processApiResponse(response)
            }
    }

    fun processSeasonEpisodeData(id: Int, season: Int, episode: Int, restApiCall: RestApiSeasonEpisodeCall<T>) {
        val exceptionHandler = CoroutineExceptionHandler { _, ex ->
            onError("Coroutine exception ${ex.localizedMessage}")
        }

        CoroutineScope(Dispatchers.IO + exceptionHandler)
            .launch {
                val response = restApiCall(id, season, episode)
                processApiResponse(response)
            }
    }

    private fun processApiResponse(response: Response<T>) {
        CoroutineScope(Dispatchers.Main).launch {
            if (response.isSuccessful) {
                if (response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    onError("Null response body")
                }
            } else {
                onError("Error : ${response.message()} ")
            }
        }
    }
}