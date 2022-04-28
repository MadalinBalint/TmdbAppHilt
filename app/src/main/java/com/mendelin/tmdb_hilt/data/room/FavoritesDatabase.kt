package com.mendelin.tmdb_hilt.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mendelin.tmdb_hilt.data.model.entity.MovieListResultEntity
import com.mendelin.tmdb_hilt.data.model.entity.TvListResultEntity
import dagger.hilt.android.qualifiers.ApplicationContext


@Database(entities = [MovieListResultEntity::class, TvListResultEntity::class], version = 2)
@TypeConverters(ConverterIntList::class, ConverterStringList::class)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        private var INSTANCE: FavoritesDatabase? = null

        fun getDatabase(@ApplicationContext context: Context): FavoritesDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    FavoritesDatabase::class.java, "FavoritesDB"
                ).build()
            }
            return INSTANCE!!
        }
    }
}