package com.goldbach.absolutecinema.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.goldbach.absolutecinema.MainDispatcherRule
import com.goldbach.absolutecinema.data.dto.MovieDto
import com.goldbach.absolutecinema.data.dto.MovieResponse
import com.goldbach.absolutecinema.data.models.Movie
import com.goldbach.absolutecinema.data.repositories.MovieApiRepository
import com.goldbach.absolutecinema.data.repositories.MovieDbRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dbRepository: MovieDbRepository

    @Mock
    private lateinit var apiRepository: MovieApiRepository

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        homeViewModel = HomeViewModel(apiRepository, dbRepository)
    }


    @Test
    fun `getRecentlyReleasedMoviesAndPopularSeries should return Success when API returns data`() =
        runTest {
            // GIVEN
            val responseMovies = listOf(MovieDto("1", "Movie 1", "Desc", "", ""))
            val responseSeries = listOf(MovieDto("2", "Serie 1", "Desc", "", ""))

            // WHEN
            `when`(apiRepository.getRecentlyReleasedMovies()).thenReturn(
                Response.success(
                    MovieResponse(responseMovies)
                )
            )
            `when`(apiRepository.getPopularSeries()).thenReturn(
                Response.success(
                    MovieResponse(
                        responseSeries
                    )
                )
            )

            homeViewModel.getRecentlyReleasedMoviesAndPopularSeries()

            // THEN
            val actualState = homeViewModel.movieUiState
            assertNotNull(actualState)
            if (actualState is MovieUiState.Success) {
                assertEquals(responseMovies, actualState.movies)
                assertEquals(responseSeries, actualState.series)
            } else {
                fail("Expected state to be Success bu was $actualState")
            }
        }

    @Test
    fun `movieDtoToMovie should mapper and return a MovieDto to Movie`() = runTest {
        // GIVEN
        val movieDto = MovieDto("1","MTitle","MDesc","","")
        val expected = Movie(0, "MTitle", "MDesc", "", "")

        // WHEN
        val actual = homeViewModel.movieDtoToMovie(movieDto)

        // THEN
        assertEquals(expected, actual)
    }









}