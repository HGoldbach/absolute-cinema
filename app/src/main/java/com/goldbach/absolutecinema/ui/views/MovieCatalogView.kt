package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goldbach.absolutecinema.ui.AppViewModelProvider
import com.goldbach.absolutecinema.ui.MovieBottomAppBar
import com.goldbach.absolutecinema.ui.MovieTopAppBar
import com.goldbach.absolutecinema.ui.components.ErrorCatalog
import com.goldbach.absolutecinema.ui.components.LoadingCatalog
import com.goldbach.absolutecinema.ui.components.SuccessCatalogGrid
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination
import com.goldbach.absolutecinema.ui.viewmodels.MovieGenreViewModel
import com.goldbach.absolutecinema.ui.viewmodels.MovieUiState

object MovieGenreDestination : NavigationDestination {
    override val route = "movie"
    override val title = "Movies by Genre"
    const val genreIdArg = "genreId"
    val routeWithArgs = "${route}/{${genreIdArg}}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieGenreView(
    canNavigateBack: Boolean = true,
    navigateUp: () -> Unit,
    navigateToSeries: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieGenreViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.movieUiState
    Scaffold(
        topBar = {
            MovieTopAppBar(
                title = MovieGenreDestination.title,
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp
            )
        },
        bottomBar = {
            MovieBottomAppBar(
                navigateToHome = navigateToHome,
                navigateToMovies = navigateUp,
                navigateToSeries = navigateToSeries,
                navigateToSearch = navigateToSearch,
                currentlyRoute = MovieGenreDestination.title
            )
        }
    ) {
        when (uiState) {
            is MovieUiState.Loading -> LoadingCatalog(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )

            is MovieUiState.Error -> ErrorCatalog(
                retryAction = viewModel::getMoviesByGenre,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )

            is MovieUiState.Success -> SuccessCatalogGrid(
                movieList = uiState.movies,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    }
}
