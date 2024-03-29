package com.goldbach.absolutecinema.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldbach.absolutecinema.data.dto.GenreDto
import com.goldbach.absolutecinema.data.repositories.MovieApiRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface MenuUiState {
    data class Success(val genres: List<GenreDto>) : MenuUiState
    object Loading : MenuUiState
    object Error : MenuUiState
}



class MovieMenuViewModel(private val movieRepository: MovieApiRepository) : ViewModel() {

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
                    movieRepository.getGenres("Movie").body()!!.genres
                )
            } catch (e: IOException) {
                MenuUiState.Error
            } catch (e: HttpException) {
                MenuUiState.Error
            }
        }
    }

}