package com.goldbach.absolutecinema.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.goldbach.absolutecinema.MovieApplication
import com.goldbach.absolutecinema.ui.viewmodels.HomeViewModel
import com.goldbach.absolutecinema.ui.viewmodels.MovieCatalogViewModel
import com.goldbach.absolutecinema.ui.viewmodels.MovieMenuViewModel
import com.goldbach.absolutecinema.ui.viewmodels.ProfileFavoritesViewModel
import com.goldbach.absolutecinema.ui.viewmodels.ProfileListViewModel
import com.goldbach.absolutecinema.ui.viewmodels.SearchViewModel
import com.goldbach.absolutecinema.ui.viewmodels.SerieCatalogViewModel
import com.goldbach.absolutecinema.ui.viewmodels.SerieMenuViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                movieApplication().container.movieApiRepository,
                movieApplication().container.movieDbRepository
            )
        }
        initializer {
            MovieMenuViewModel(
                movieApplication().container.movieApiRepository
            )
        }
        initializer {
            MovieCatalogViewModel(
                this.createSavedStateHandle(),
                movieApplication().container.movieApiRepository,
                movieApplication().container.movieDbRepository
            )
        }
        initializer {
            SerieMenuViewModel(
                movieApplication().container.movieApiRepository
            )
        }
        initializer {
            SerieCatalogViewModel(
                this.createSavedStateHandle(),
                movieApplication().container.movieApiRepository,
                movieApplication().container.movieDbRepository
            )
        }
        initializer {
            SearchViewModel(
                movieApplication().container.movieApiRepository,
                movieApplication().container.movieDbRepository
            )
        }
        initializer {
            ProfileListViewModel(
                movieApplication().container.movieDbRepository
            )
        }
        initializer {
            ProfileFavoritesViewModel(
                movieApplication().container.movieDbRepository
            )
        }
    }
}

fun CreationExtras.movieApplication(): MovieApplication =
    (this[APPLICATION_KEY] as MovieApplication)