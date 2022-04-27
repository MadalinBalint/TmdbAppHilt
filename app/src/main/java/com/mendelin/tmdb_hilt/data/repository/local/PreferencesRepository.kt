package com.mendelin.tmdb_hilt.data.repository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor(@ApplicationContext val context: Context) {
    private val Context.preferencesDataSource: DataStore<Preferences> by preferencesDataStore(name = "preferences")

    suspend fun <T> updatePosition(key: Preferences.Key<T>, value: T) {
        context.preferencesDataSource.edit { preferences ->
            preferences[key] = value

            Timber.e("Updated ${key.name}, $value")
        }
    }

    fun <T> getPostion(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.preferencesDataSource.data
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
    }

    companion object Key {
        val KEY_HOME = intPreferencesKey("KEY_HOME")
        val KEY_MOVIES_NOW_PLAYING = intPreferencesKey("KEY_MOVIES_NOW_PLAYING")
        val KEY_MOVIES_POPULAR = intPreferencesKey("KEY_MOVIES_POPULAR")
        val KEY_MOVIES_UPCOMING = intPreferencesKey("KEY_MOVIES_UPCOMING")
        val KEY_TV_ON_THE_AIR = intPreferencesKey("KEY_TV_ON_THE_AIR")
        val KEY_TV_POPULAR = intPreferencesKey("KEY_TV_POPULAR")
        val KEY_TV_TOP_RATED = intPreferencesKey("KEY_TV_TOP_RATED")

        const val ITEMS_PER_PAGE = 20
    }
}