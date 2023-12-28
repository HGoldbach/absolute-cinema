package com.goldbach.absolutecinema.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldbach.absolutecinema.data.models.Movie
import com.goldbach.absolutecinema.data.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SearchViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isActiveSearch = MutableStateFlow(false)
    val isActiveSearch = _isActiveSearch.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _results = MutableStateFlow(emptyList<Movie>())
    val results = _results.asStateFlow()

    fun searchMovies(title: String) {
        if (title.isEmpty()) {
            _results.value = emptyList()
        } else {
            viewModelScope.launch {
                _results.value = movieRepository.getMoviesAndSeriesByTitle(title).body()!!.results
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun changeActiveSearch() {
        _isActiveSearch.value = !_isActiveSearch.value
    }

}

