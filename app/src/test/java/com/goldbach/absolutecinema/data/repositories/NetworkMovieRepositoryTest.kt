package com.goldbach.absolutecinema.data.repositories

import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.dto.GenreDto
import com.goldbach.absolutecinema.data.dto.GenreResponse
import com.goldbach.absolutecinema.data.dto.MovieDto
import com.goldbach.absolutecinema.data.dto.MovieResponse
import com.goldbach.absolutecinema.data.network.MovieApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class NetworkMovieRepositoryTest {

    @Mock
    lateinit var movieApiService: MovieApiService
    private lateinit var repository: MovieApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = NetworkMovieRepository(movieApiService)
    }

    @Test
    fun `getRecentlyReleasedMovies should call movieApiService_getRecentlyReleasedMovies`() = runBlocking {
        // GIVEN
        val response = MovieResponse(listOf(
            MovieDto("1","Test Movie","test","","")
        ))

        // WHEN
        `when`(movieApiService.getRecentlyReleasedMovies(Constants.API_KEY)).thenReturn(Response.success(response))
        val result = repository.getRecentlyReleasedMovies()

        // THEN
        assertEquals(response, result.body())
    }

    @Test
    fun `getMoviesByGenre should call movieApiService_getMoviesByGenre`() = runBlocking {
        // GIVEN
        val response = MovieResponse(listOf(
            MovieDto("1","Test Movie","test","",""),
            MovieDto("2","Test Movie","test","","")
        ))

        // WHEN
        `when`(movieApiService.getMoviesByGenre(0,Constants.API_KEY)).thenReturn(Response.success(response))
        val result = repository.getMoviesByGenre(0)

        // THEN
        assertEquals(response, result.body())
    }

    @Test
    fun `getPopularSeries should call movieApiService_getPopularSeries`() = runBlocking {
        // GIVEN
        val response = MovieResponse(listOf(
            MovieDto("1","Test Series","test","",""),
        ))

        // WHEN
        `when`(movieApiService.getPopularSeries(Constants.API_KEY)).thenReturn(Response.success(response))
        val result = repository.getPopularSeries()

        // THEN
        assertEquals(response, result.body())
    }

    @Test
    fun `getSeriesByGenre should call movieApiService_getSeriesByGenre`() = runBlocking {
        // GIVEN
        val response = MovieResponse(listOf(
            MovieDto("1","Test Series","test","",""),
            MovieDto("2","Test Series","test","","")
        ))

        // WHEN
        `when`(movieApiService.getSeriesByGenre(0, Constants.API_KEY)).thenReturn(Response.success(response))
        val result = repository.getSeriesByGenre(0)

        // THEN
        assertEquals(response, result.body())
    }

    @Test
    fun `getGenres should call movieApiService_getGenres`() = runBlocking {
        // GIVEN
        val response = GenreResponse(listOf(
            GenreDto(0,"Action")
        ))

        // WHEN
        `when`(movieApiService.getSeriesGenres(Constants.API_KEY)).thenReturn(Response.success(response))
        val result = repository.getGenres("Series")

        // THEN
        assertEquals(response, result.body())
    }

    @Test
    fun `getMoviesAndSeriesByTitle should call movieApiService_getMoviesAndSeriesByTitle`() = runBlocking {
        // GIVEN
        val response = MovieResponse(listOf(
            MovieDto("1","Test Series","test","",""),
            MovieDto("2","Test Movies","test","","")
        ))

        // WHEN
        `when`(movieApiService.searchMoviesAndSeries("Test",Constants.API_KEY)).thenReturn(Response.success(response))
        val result = repository.getMoviesAndSeriesByTitle("Test")

        // THEN
        assertEquals(response, result.body())

    }
}