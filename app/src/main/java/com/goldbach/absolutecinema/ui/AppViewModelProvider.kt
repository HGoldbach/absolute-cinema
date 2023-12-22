package com.goldbach.absolutecinema.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.goldbach.absolutecinema.MovieApplication
import com.goldbach.absolutecinema.ui.viewmodels.HomeViewModel
import com.goldbach.absolutecinema.ui.viewmodels.MovieMenuViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                movieApplication().container.movieRepository
            )
        }
        initializer {
            MovieMenuViewModel(
                movieApplication().container.movieRepository
            )
        }
    }
}

fun CreationExtras.movieApplication(): MovieApplication =
    (this[APPLICATION_KEY] as MovieApplication)