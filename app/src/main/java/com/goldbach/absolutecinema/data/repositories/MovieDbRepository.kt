package com.goldbach.absolutecinema.data.repositories

import com.goldbach.absolutecinema.data.dao.MovieDao
import com.goldbach.absolutecinema.data.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDbRepository {
    fun getAllMoviesStream(): Flow<List<Movie>>
    suspend fun insertMovie(movie: Movie)
}

class OfflineMovieDbRepository(private val movieDao: MovieDao) : MovieDbRepository {
    override fun getAllMoviesStream(): Flow<List<Movie>> {
        return movieDao.getAllMovies()
    }

    override suspend fun insertMovie(movie: Movie) {
        return movieDao.insertMovie(movie)
    }

}