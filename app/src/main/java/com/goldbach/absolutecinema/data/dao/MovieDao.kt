package com.goldbach.absolutecinema.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.goldbach.absolutecinema.data.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY title ASC")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE favorite == 0 ORDER BY title ASC")
    fun getAllSavedMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE favorite == 1 ORDER BY title ASC")
    fun getAllFavoritesMovies(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun removeMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)
}