package com.goldbach.absolutecinema.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldbach.absolutecinema.data.dto.MovieDto
import com.goldbach.absolutecinema.data.models.Movie
import com.goldbach.absolutecinema.data.repositories.MovieDbRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProfileListViewModel(
    private val movieDbRepository: MovieDbRepository
) : ViewModel() {

    val profileUiState: StateFlow<ProfileUiState> =
        movieDbRepository.getAllSavedMoviesStream()
            .map { ProfileUiState(it.map { movieToMovieDto(it) }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ProfileUiState()
            )

    suspend fun removeMovie(movieDto: MovieDto) {
        movieDbRepository.removeMovie(movieDtoToMovie(movieDto))
    }

    suspend fun setMovieToFavorites(movieDto: MovieDto) {
        movieDbRepository.updateMovie(movieDtoToMovie(movieDto, true))
    }
}

fun movieDtoToMovie(movieDto: MovieDto, setFavorite: Boolean = false) : Movie {
    return Movie(
        id = movieDto.id.toInt(),
        title = movieDto.title,
        description = movieDto.description,
        poster = movieDto.poster,
        releaseDate = movieDto.releaseDate,
        isFavorite = setFavorite
    )
}

fun movieToMovieDto(movie: Movie, setFavorite: Boolean = false): MovieDto {
    return MovieDto(
        id = movie.id.toString(),
        title = movie.title,
        description = movie.description,
        poster = movie.poster,
        releaseDate = movie.releaseDate,
        isFavorite = setFavorite
    )
}

data class ProfileUiState(
    val moviesList: List<MovieDto> = listOf()
)