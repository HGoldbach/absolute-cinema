package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.goldbach.absolutecinema.ui.viewmodels.SerieMenuViewModel

object SerieMenuDestination : NavigationDestination {
    override val route = "serie_menu"
    override val title = "Series Genres"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SerieMenuView(
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToMovies: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToGenreSelected: (Int) -> Unit,
    viewModel: SerieMenuViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val menuUiState = viewModel.menuUiState
    Scaffold(
        topBar = {
            MovieTopAppBar(
                title = SerieMenuDestination.title,
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp
            )
        },
        bottomBar = {
            MovieBottomAppBar(
                navigateToHome = navigateToHome,
                navigateToMovies = navigateToMovies,
                navigateToSearch = navigateToSearch,
                navigateToProfile = navigateToProfile,
                currentlyRoute = SerieMenuDestination.title
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
                genres = menuUiState.genres,
                navigateToGenreSelected = navigateToGenreSelected,
                modifier = Modifier.padding(it)
            )
        }
    }
}