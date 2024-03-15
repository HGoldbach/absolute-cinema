package com.goldbach.absolutecinema.data.repositories

import com.goldbach.absolutecinema.data.dao.MovieDao
import com.goldbach.absolutecinema.data.models.Movie
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class OfflineMovieDbRepositoryTest {

    private lateinit var movieDao: MovieDaoFake
    private lateinit var repository: MovieDbRepository

    private val movies = listOf(
        Movie(1,"Movie 1", "Desc", "", ""),
        Movie(2,"Movie 2", "Desc", "", ""),
        Movie(3,"Movie 3", "Desc", "", ""),
        Movie(4,"Movie 4", "Desc", "", ""),
        Movie(5,"Movie 5", "Desc", "", "", true),
        Movie(6,"Movie 6", "Desc", "", "", true),
        Movie(7,"Movie 7", "Desc", "", "", true),
    )

    private val favoritesMovies = listOf(
        movies[4],
        movies[5],
        movies[6]
    )

    private val savedMovies = listOf(
        movies[0],
        movies[1],
        movies[2],
        movies[3]
    )

    @Before
    fun setUp() {
        movieDao = MovieDaoFake(movies.toMutableList())
        repository = OfflineMovieDbRepository(movieDao)
    }

    @Test
    fun getAllMovies_requestAllMoviesFromDb() = runTest {
        val result = repository.getAllMoviesStream().first()
        assertEquals(movies, result)
    }

    @Test
    fun getAllSavedMovies_requestAllSavedMoviesFromDb() = runTest {
        val result = repository.getAllSavedMoviesStream().first()
        assertEquals(savedMovies, result)
    }

    @Test
    fun getAllFavoritesMovies_requestAllFavoritesMoviesFromDb() = runTest {
        val result = repository.getAllFavoritesMoviesStream().first()
        assertEquals(favoritesMovies, result)
    }

    @Test
    fun insertMovie_insertMovieIntoDb() = runTest {
        val movie = Movie(8,"Movie 8", "Desc", "", "")
        repository.insertMovie(movie)

        val result = repository.getAllMoviesStream().first()
        assertNotEquals(movies.size, result.size)
    }

    @Test
    fun deleteMovie_deleteMovieFromDb() = runTest {
        val movie = movies[0]
        repository.removeMovie(movie)

        val result = repository.getAllMoviesStream().first()
        assertNotEquals(movies.size, result.size)
    }

    @Test
    fun updateMovie_updateMovieFromDb() = runTest {
        val movie = savedMovies[0]
        movie.isFavorite = true
        repository.updateMovie(movie)

        val savedMoviesResult = repository.getAllSavedMoviesStream().first()
        val favoritedMoviesResult = repository.getAllFavoritesMoviesStream().first()

        assertNotEquals(savedMovies.size, savedMoviesResult.size)
        assertNotEquals(favoritesMovies.size, favoritedMoviesResult.size)

    }

}