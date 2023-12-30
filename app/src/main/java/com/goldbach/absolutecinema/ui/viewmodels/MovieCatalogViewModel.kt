package com.goldbach.absolutecinema.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldbach.absolutecinema.data.dto.MovieDto
import com.goldbach.absolutecinema.data.models.Movie
import com.goldbach.absolutecinema.data.repositories.MovieApiRepository
import com.goldbach.absolutecinema.data.repositories.MovieDbRepository
import com.goldbach.absolutecinema.ui.views.MovieGenreDestination
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MovieCatalogViewModel(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieApiRepository,
    private val movieDbRepository: MovieDbRepository
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

    suspend fun saveMovie(movie: MovieDto) {
        movieDbRepository.insertMovie(movieDtoToMovie(movie))
    }

    fun movieDtoToMovie(movie: MovieDto): Movie {
        return Movie(
            0,
            title = movie.title,
            description = movie.description,
            poster = movie.poster,
            releaseDate = movie.releaseDate
        )
    }


}