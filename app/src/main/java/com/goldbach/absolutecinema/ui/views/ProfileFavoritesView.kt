package com.goldbach.absolutecinema.ui.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goldbach.absolutecinema.data.dto.MovieDto
import com.goldbach.absolutecinema.ui.AppViewModelProvider
import com.goldbach.absolutecinema.ui.MovieBottomAppBar
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme
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
    val context = LocalContext.current
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
        ProfileFavoritesScreen(
            moviesList = uiState.moviesList,
            onRemove = { movie ->
                coroutineScope.launch {
                    viewModel.removeMovie(movie)
                    Toast.makeText(context, "Movie removed from your list", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            onFavorite = { movie ->
                coroutineScope.launch {
                    viewModel.setMovieToUnfavorites(movie)
                    Toast.makeText(context, "Movie removed from your favorites", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            modifier = modifier.padding(it)
        )
//        ListGrid(
//            moviesList = uiState.moviesList,
//            onRemove = {
//                coroutineScope.launch {
//                    viewModel.removeMovie(it)
//                    Toast.makeText(context, "Movie removed from your list", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            },
//            onFavorite = {
//                coroutineScope.launch {
//                    viewModel.setMovieToUnfavorites(it)
//                    Toast.makeText(context, "Movie removed from your favorites", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            },
//            modifier = modifier.padding(it)
//        )
    }
}

@Composable
fun ProfileFavoritesScreen(
    moviesList: List<MovieDto>,
    onRemove: (MovieDto) -> Unit,
    onFavorite: (MovieDto) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Favorite - Movies and Shows",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .paddingFromBaseline(top = 50.dp, bottom = 12.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        if(moviesList.isEmpty()) {
            Text(
                text = "Your favorite list is empty.\nFavorite the movies/shows in your list to appear here",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(vertical = 100.dp, horizontal = 16.dp)
                    .fillMaxSize(),
                textAlign = TextAlign.Center
            )
        } else {
            ListGrid(
                moviesList = moviesList,
                onRemove = onRemove,
                onFavorite = onFavorite
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileFavoritesScreenPreview() {
    AbsoluteCinemaTheme {
        val mockData = List(10) { MovieDto("$it","$it - Movie","","","") }
        ProfileListScreen(moviesList = mockData, onRemove = {}, onFavorite = {})
    }
}

