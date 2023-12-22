package com.goldbach.absolutecinema.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldbach.absolutecinema.data.models.Genre
import com.goldbach.absolutecinema.data.repositories.MovieRepository
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface MenuUiState {
    data class Success(val genres: List<Genre>) : MenuUiState
    object Loading : MenuUiState
    object Error : MenuUiState
}



class MovieMenuViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    var movieUiState: MenuUiState by mutableStateOf(MenuUiState.Loading)
        private set

    init {
        getGenres()
    }

    fun getGenres() {
        viewModelScope.launch {
            movieUiState = MenuUiState.Loading
            movieUiState = try {
                MenuUiState.Success(
                    movieRepository.getGenres().body()!!.genres
                )
            } catch (e: IOException) {
                MenuUiState.Error
            }
        }
    }

}