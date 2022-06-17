package com.mendelin.tmdb_hilt.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {
    val error = MutableLiveData<String>()
    val isLoading = MutableLiveData(false)

    fun getErrorFilter(): LiveData<String> = error
    fun getLoadingObservable(): LiveData<Boolean> = isLoading

    fun onErrorHandled() {
        error.value = ""
    }

    var firstLoad: Boolean = true

    fun setUiState(state: CombinedLoadStates) {
        isLoading.value = state.refresh is LoadState.Loading

        val errorState = state.refresh as? LoadState.Error
            ?: state.source.append as? LoadState.Error
            ?: state.source.prepend as? LoadState.Error
            ?: state.append as? LoadState.Error
            ?: state.prepend as? LoadState.Error

        errorState?.let {
            error.value = it.error.message
        }
    }
}