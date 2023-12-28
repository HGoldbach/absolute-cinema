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
                currentlyRoute = SerieMenuDestination.title
            )
        }

    ) {
        when(menuUiState) {
            is MenuUiState.Loading -> MenuLoadingScreen(modifier = Modifier
                .fillMaxSize()
                .padding(it))
            is MenuUiState.Error -> MenuErrorScreen(modifier = Modifier
                .fillMaxSize()
                .padding(it))
            is MenuUiState.Success -> MenuSuccessScreen(
                genres = menuUiState.genres,
                navigateToGenreSelected = navigateToGenreSelected,
                modifier = Modifier.padding(it)
            )
        }
    }
}