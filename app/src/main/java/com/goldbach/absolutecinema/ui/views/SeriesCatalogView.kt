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
import com.goldbach.absolutecinema.ui.viewmodels.MovieUiState
import com.goldbach.absolutecinema.ui.viewmodels.SerieGenreViewModel

object SerieGenreDestination : NavigationDestination {
    override val route = "serie_genre"
    override val title = "Series by Genre"
    const val genreIdArg = "genreId"
    val routeWithArgs = "${route}/{${genreIdArg}}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SerieGenreView(
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToMovies: () -> Unit,
    navigateToSearch: () -> Unit,
    viewModel: SerieGenreViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState: MovieUiState = viewModel.movieUiState
    Scaffold(
        topBar = {
            MovieTopAppBar(
                title = SerieGenreDestination.title,
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp
            )
        },
        bottomBar = {
            MovieBottomAppBar(
                navigateToHome = navigateToHome,
                navigateToSeries = navigateUp,
                navigateToMovies = navigateToMovies,
                navigateToSearch = navigateToSearch,
                currentlyRoute = SerieGenreDestination.title
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
                retryAction = viewModel::getSerieByGenre,
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