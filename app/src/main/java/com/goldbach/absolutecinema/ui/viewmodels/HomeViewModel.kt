package com.goldbach.absolutecinema.ui.viewmodels

import retrofit2.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldbach.absolutecinema.data.models.Movie
import com.goldbach.absolutecinema.data.repositories.MovieRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MovieUiState {
    data class Success(val movies: List<Movie>, val series: List<Movie> = emptyList()) : MovieUiState
    object Error : MovieUiState
    object Loading : MovieUiState
}

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    var movieUiState: MovieUiState by mutableStateOf(MovieUiState.Loading)
        private set

    init {
        getRecentlyReleasedMoviesAndPopularSeries()
    }

    private fun getRecentlyReleasedMoviesAndPopularSeries() {
        viewModelScope.launch {
            movieUiState = MovieUiState.Loading
            movieUiState = try {
                MovieUiState.Success(
                    movies = movieRepository.getRecentlyReleasedMovies().body()!!.results,
                    series = movieRepository.getPopularSeries().body()!!.results
                )
            } catch (e: IOException) {
                MovieUiState.Error
            } catch (e: HttpException) {
                MovieUiState.Error
            }
        }
    }

}