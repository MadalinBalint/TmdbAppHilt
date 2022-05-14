package com.mendelin.tmdb_hilt.domain.models.rest_api

import androidx.annotation.Keep

@Keep
data class SpokenLanguageItem(val iso_639_1: String, val name: String)