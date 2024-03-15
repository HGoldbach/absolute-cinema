package com.goldbach.absolutecinema.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.goldbach.absolutecinema.data.database.MovieDatabase
import com.goldbach.absolutecinema.data.models.Movie
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var movieDao: MovieDao
    private lateinit var movieDatabase: MovieDatabase
    private var moviesList = listOf(
        Movie(1,"Pirates of Caribbean", "Some description about the movie...", "","", false),
        Movie(2,"Pirates of Caribbean 2", "Some description about the movie...", "","", true),
        Movie(3,"Pirates of Caribbean 3", "Some description about the movie...", "","", true),
        Movie(4,"Pirates of Caribbean 4", "Some description about the movie...", "","", true),
        Movie(5,"Pirates of Caribbean 5", "Some description about the movie...", "","", true),
        Movie(6,"Pirates of Caribbean 6", "Some description about the movie...", "","", false),
    )

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        movieDatabase = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        movieDao = movieDatabase.MovieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        movieDatabase.close()
    }

    @Test
    fun daoInsert_insertMovieIntoDb() = runBlocking {
        movieDao.insertMovie(moviesList[0])
        val result = movieDao.getAllMovies().first()
        assertEquals(moviesList[0], result[0])
    }

    @Test
    fun daoGet_getAllMoviesFromDb() = runBlocking {
        moviesList.forEach { movie ->
            movieDao.insertMovie(movie)
        }
        val result = movieDao.getAllMovies().first()
        assertEquals(moviesList.size, result.size)
    }

    @Test
    fun daoGet_getAllSavedMoviesFromDb() = runBlocking {
        moviesList.filter { movie ->
            !movie.isFavorite
        }.forEach { movie ->
            movieDao.insertMovie(movie)
        }
        val result = movieDao.getAllSavedMovies().first()
        assertEquals(moviesList.filter { !it.isFavorite }.size, result.size)
    }

    @Test
    fun daoGet_getAllFavoritesMoviesFromDb() = runBlocking {
        moviesList.filter { movie ->
            movie.isFavorite
        }.forEach {  movie ->
            movieDao.insertMovie(movie)
        }

        val result = movieDao.getAllFavoritesMovies().first()
        assertEquals(moviesList.filter { it.isFavorite }.size, result.size)
    }

    @Test
    fun daoRemove_removeMovieFromDb() = runBlocking {
        val movie = moviesList[0]
        movieDao.insertMovie(movie)
        var result = movieDao.getAllMovies().first()
        assertEquals(movie, result[0])

        movieDao.removeMovie(movie)
        result = movieDao.getAllMovies().first()
        assertEquals(0, result.size)
    }

    @Test
    fun daoUpdate_updateMovieFromDb() = runBlocking {
        val movie = moviesList[0]
        movieDao.insertMovie(movie)
        var result = movieDao.getAllSavedMovies().first()
        assertEquals(movie, result[0])

        movie.isFavorite = true
        movieDao.updateMovie(movie)
        result = movieDao.getAllFavoritesMovies().first()
        assertEquals(movie, result[0])
    }
}