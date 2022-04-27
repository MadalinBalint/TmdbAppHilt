package com.mendelin.tmdb_hilt.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
}