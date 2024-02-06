package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goldbach.absolutecinema.ui.AppViewModelProvider
import com.goldbach.absolutecinema.ui.MovieBottomAppBar
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination
import com.goldbach.absolutecinema.ui.viewmodels.ProfileFavoritesViewModel
import kotlinx.coroutines.launch

object ProfileFavoritesDestination : NavigationDestination {
    override val route = "profile_favorites"
    override val title = "profile_favorites"
}

@Composable
fun ProfileFavoritesView(
    navigateToHome: () -> Unit,
    navigateToMovies: () -> Unit,
    navigateToSeries: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileFavoritesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.profileUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        bottomBar = {
            MovieBottomAppBar(
                navigateToHome = navigateToHome,
                navigateToMovies = navigateToMovies,
                navigateToSeries = navigateToSeries,
                navigateToSearch = navigateToSearch,
                navigateToProfile = navigateToProfile,
                currentlyRoute = ProfileFavoritesDestination.route
            )
        }
    ) {
        ListGrid(
            moviesList = uiState.moviesList,
            onRemove = {
                coroutineScope.launch {
                    viewModel.removeMovie(it)
                }
            },
            onFavorite = {
                coroutineScope.launch {
                    viewModel.setMovieToUnfavorites(it)
                }
            },
            modifier = modifier.padding(it)
        )
    }
}