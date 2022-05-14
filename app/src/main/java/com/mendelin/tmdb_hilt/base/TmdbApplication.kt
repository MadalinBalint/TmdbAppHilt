package com.mendelin.tmdb_hilt.base

import android.app.Application
import com.mendelin.tmdb_hilt.domain.logging.TimberPlant
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TmdbApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        TimberPlant.plantTimberDebugLogger()
    }
}