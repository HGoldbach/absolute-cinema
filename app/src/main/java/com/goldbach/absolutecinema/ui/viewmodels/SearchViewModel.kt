package com.goldbach.absolutecinema.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldbach.absolutecinema.data.dto.MovieDto
import com.goldbach.absolutecinema.data.repositories.MovieApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val movieRepository: MovieApiRepository) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isActiveSearch = MutableStateFlow(false)
    val isActiveSearch = _isActiveSearch.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _results = MutableStateFlow(emptyList<MovieDto>())
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

