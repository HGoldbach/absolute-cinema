package com.goldbach.absolutecinema.data.repositories

import com.goldbach.absolutecinema.data.dao.MovieDao
import com.goldbach.absolutecinema.data.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDbRepository {
    fun getAllMoviesStream(): Flow<List<Movie>>
    fun getAllSavedMoviesStream(): Flow<List<Movie>>
    fun getAllFavoritesMoviesStream(): Flow<List<Movie>>
    suspend fun insertMovie(movie: Movie)
    suspend fun removeMovie(movie: Movie)
    suspend fun updateMovie(movie: Movie)
}

class OfflineMovieDbRepository(private val movieDao: MovieDao) : MovieDbRepository {
    override fun getAllMoviesStream(): Flow<List<Movie>> {
        return movieDao.getAllMovies()
    }
    override fun getAllSavedMoviesStream(): Flow<List<Movie>> {
        return movieDao.getAllSavedMovies()
    }
    override fun getAllFavoritesMoviesStream(): Flow<List<Movie>> {
        return movieDao.getAllFavoritesMovies()
    }
    override suspend fun insertMovie(movie: Movie) {
        return movieDao.insertMovie(movie)
    }
    override suspend fun removeMovie(movie: Movie) {
        return movieDao.removeMovie(movie)
    }
    override suspend fun updateMovie(movie: Movie) {
        return movieDao.updateMovie(movie)
    }

}