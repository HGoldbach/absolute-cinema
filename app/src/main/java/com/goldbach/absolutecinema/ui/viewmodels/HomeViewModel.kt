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
    data class Success(val movies: List<Movie>) : MovieUiState
    object Error : MovieUiState
    object Loading : MovieUiState
}

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    var movieUiState: MovieUiState by mutableStateOf(MovieUiState.Loading)
        private set

    var serieUiState: MovieUiState by mutableStateOf(MovieUiState.Loading)
        private set

    init {
        getRecentlyReleasedMovies()
        getPopularSeries()
    }

    private fun getRecentlyReleasedMovies() {
        viewModelScope.launch {
            movieUiState = MovieUiState.Loading
            movieUiState = try {
                MovieUiState.Success(
                    movieRepository.getRecentlyReleasedMovies().body()!!.results
                )
            } catch (e: IOException) {
                MovieUiState.Error
            } catch (e: HttpException) {
                MovieUiState.Error
            }
        }
    }

    private fun getPopularSeries() {
        viewModelScope.launch {
            serieUiState = MovieUiState.Loading
            serieUiState = try {
                MovieUiState.Success(
                    movieRepository.getPopularSeries().body()!!.results
                )
            } catch (e: IOException) {
                MovieUiState.Error
            } catch (e: HttpException) {
                MovieUiState.Error
            }
        }
    }

}