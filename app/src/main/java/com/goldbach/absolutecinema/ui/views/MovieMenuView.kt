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
import com.goldbach.absolutecinema.ui.components.ErrorGenre
import com.goldbach.absolutecinema.ui.components.LoadingGenre
import com.goldbach.absolutecinema.ui.components.SuccessGenreGrid
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination
import com.goldbach.absolutecinema.ui.viewmodels.MenuUiState
import com.goldbach.absolutecinema.ui.viewmodels.MovieMenuViewModel

object MovieMenuDestination : NavigationDestination {
    override val route = "movie_menu"
    override val title = "Movie Genres"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieMenuView(
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToSeries: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToGenreSelected: (Int) -> Unit,
    viewModel: MovieMenuViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val menuUiState = viewModel.movieUiState
    Scaffold(
        topBar = {
            MovieTopAppBar(
                title = MovieMenuDestination.title,
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp
            )
        },
        bottomBar = {
            MovieBottomAppBar(
                navigateToHome = navigateToHome,
                navigateToSeries = navigateToSeries,
                navigateToSearch = navigateToSearch,
                navigateToProfile = navigateToProfile,
                currentlyRoute = MovieMenuDestination.title
            )
        }
    ) {
        when (menuUiState) {
            is MenuUiState.Loading -> LoadingGenre(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )

            is MenuUiState.Error -> ErrorGenre(
                retryAction = viewModel::getGenres,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )

            is MenuUiState.Success -> SuccessGenreGrid(
                menuUiState.genres,
                navigateToGenreSelected = navigateToGenreSelected,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    }
}
