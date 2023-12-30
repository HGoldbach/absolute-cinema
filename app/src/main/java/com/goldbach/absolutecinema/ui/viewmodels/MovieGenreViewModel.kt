package com.goldbach.absolutecinema.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldbach.absolutecinema.data.repositories.MovieApiRepository
import com.goldbach.absolutecinema.ui.views.MovieGenreDestination
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MovieGenreViewModel(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieApiRepository
) : ViewModel() {

    private val genreId: Int = checkNotNull(savedStateHandle[MovieGenreDestination.genreIdArg])
    var movieUiState: MovieUiState by mutableStateOf(MovieUiState.Loading)
        private set

    init {
        getMoviesByGenre()
    }

    fun getMoviesByGenre() {
        viewModelScope.launch {
            movieUiState = MovieUiState.Loading
            movieUiState = try {
                MovieUiState.Success(
                    movieRepository.getMoviesByGenre(genreId).body()!!.results
                )
            } catch (e: IOException) {
                MovieUiState.Error
            } catch (e: HttpException) {
                MovieUiState.Error
            }
        }
    }


}