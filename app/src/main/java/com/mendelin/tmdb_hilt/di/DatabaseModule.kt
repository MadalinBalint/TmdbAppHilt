package com.mendelin.tmdb_hilt.di

import android.content.Context
import com.mendelin.tmdb_hilt.domain.api.RetrofitServiceProvider
import com.mendelin.tmdb_hilt.data.TmdbDataSource
import com.mendelin.tmdb_hilt.data.room.FavoritesDao
import com.mendelin.tmdb_hilt.data.room.FavoritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideFavoritesDao(@ApplicationContext context: Context): FavoritesDao {
        return FavoritesDatabase.getDatabase(context).favoritesDao()
    }

    @Provides
    fun provideTmdbDataSource(): TmdbDataSource {
        return RetrofitServiceProvider.getService()
    }
}