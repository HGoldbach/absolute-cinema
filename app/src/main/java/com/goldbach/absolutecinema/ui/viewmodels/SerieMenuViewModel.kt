package com.goldbach.absolutecinema.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldbach.absolutecinema.data.repositories.MovieApiRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SerieMenuViewModel(private val movieRepository: MovieApiRepository) : ViewModel() {

    var menuUiState: MenuUiState by mutableStateOf(MenuUiState.Loading)
        private set

    init {
        getGenres()
    }

    fun getGenres() {
        viewModelScope.launch {
            menuUiState = MenuUiState.Loading
            menuUiState = try {
                MenuUiState.Success(
                    movieRepository.getGenres("Series").body()!!.genres
                )
            } catch (e: IOException) {
                MenuUiState.Error
            } catch (e: HttpException) {
                MenuUiState.Error
            }
        }
    }
}