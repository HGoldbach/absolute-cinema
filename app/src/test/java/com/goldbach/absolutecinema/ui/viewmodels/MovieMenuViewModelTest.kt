package com.goldbach.absolutecinema.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.goldbach.absolutecinema.MainDispatcherRule
import com.goldbach.absolutecinema.data.dto.GenreDto
import com.goldbach.absolutecinema.data.dto.GenreResponse
import com.goldbach.absolutecinema.data.repositories.MovieApiRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response


class MovieMenuViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiRepository: MovieApiRepository

    private val viewModel: MovieMenuViewModel by lazy {
        MovieMenuViewModel(apiRepository)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `getGenres should update MenuUiState to Success when API call returns data`() = runTest {
        // GIVEN
        val genres = listOf(
            GenreDto(1,"Genre 1")
        )

        // WHEN
        `when`(apiRepository.getGenres("Movie")).thenReturn(Response.success(GenreResponse(genres)))
        viewModel.getGenres()

        // THEN
        val actualState = viewModel.movieUiState
        assertTrue(actualState is MenuUiState.Success && actualState.genres == genres)
    }

}