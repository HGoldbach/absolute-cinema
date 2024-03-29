package com.goldbach.absolutecinema.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.goldbach.absolutecinema.data.dao.MovieDao
import com.goldbach.absolutecinema.data.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun MovieDao(): MovieDao

    companion object {
        @Volatile
        private var Instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}