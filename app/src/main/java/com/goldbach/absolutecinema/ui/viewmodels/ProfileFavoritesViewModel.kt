package com.goldbach.absolutecinema.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldbach.absolutecinema.data.dto.MovieDto
import com.goldbach.absolutecinema.data.repositories.MovieDbRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProfileFavoritesViewModel(
    private val movieDbRepository: MovieDbRepository,
) : ViewModel() {

    val profileUiState: StateFlow<ProfileUiState> =
        movieDbRepository.getAllFavoritesMoviesStream()
            .map { ProfileUiState(it.map { movieToMovieDto(it, true) }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ProfileUiState()
            )

    suspend fun removeMovie(movie: MovieDto) {
        movieDbRepository.removeMovie(movieDtoToMovie(movie))
    }

    suspend fun setMovieToUnfavorites(movie: MovieDto) {
        movieDbRepository.updateMovie(movieDtoToMovie(movie))
    }

}
