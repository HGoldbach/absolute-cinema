package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goldbach.absolutecinema.ui.AppViewModelProvider
import com.goldbach.absolutecinema.ui.MovieBottomAppBar
import com.goldbach.absolutecinema.ui.MovieTopAppBar
import com.goldbach.absolutecinema.ui.components.ErrorCatalog
import com.goldbach.absolutecinema.ui.components.LoadingCatalog
import com.goldbach.absolutecinema.ui.components.SuccessCatalogGrid
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination
import com.goldbach.absolutecinema.ui.viewmodels.MovieCatalogViewModel
import com.goldbach.absolutecinema.ui.viewmodels.MovieUiState
import kotlinx.coroutines.launch

object MovieCatalogDestination : NavigationDestination {
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
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieCatalogViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.movieUiState
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            MovieTopAppBar(
                title = MovieCatalogDestination.title,
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
                navigateToProfile = navigateToProfile,
                currentlyRoute = MovieCatalogDestination.title
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
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.saveMovie(it)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    }
}
