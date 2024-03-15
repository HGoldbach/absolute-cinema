package com.goldbach.absolutecinema.data.repositories

import com.goldbach.absolutecinema.data.dao.MovieDao
import com.goldbach.absolutecinema.data.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieDaoFake(private val movies: MutableList<Movie>? = mutableListOf()) : MovieDao {
    override fun getAllMovies(): Flow<List<Movie>> {
        return flow {
            movies?.toList()?.let { emit(it) }
        }
    }

    override fun getAllSavedMovies(): Flow<List<Movie>> {
        return flow {
            emit(movies?.filter {  movie ->
                !movie.isFavorite
            } ?: emptyList())
        }
    }

    override fun getAllFavoritesMovies(): Flow<List<Movie>> {
        return flow {
            emit(movies?.filter {  movie ->
                movie.isFavorite
            } ?: emptyList())
        }
    }

    override suspend fun insertMovie(movie: Movie) {
        movies?.add(movie)
    }

    override suspend fun removeMovie(movie: Movie) {
        movies?.remove(movie)
    }

    override suspend fun updateMovie(movie: Movie) {
        val result = movies?.find { it.id == movie.id }
        val index = movies?.indexOf(result)
        if (index != null) {
            movies?.set(index, movie)
        }
    }
}