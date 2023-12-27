package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.models.Movie
import com.goldbach.absolutecinema.ui.AppViewModelProvider
import com.goldbach.absolutecinema.ui.MovieBottomAppBar
import com.goldbach.absolutecinema.ui.MovieTopAppBar
import com.goldbach.absolutecinema.ui.components.MovieModal
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
                currentlyRoute = MovieGenreDestination.title
            )
        }
    ) {
        when(uiState) {
            is MovieUiState.Loading -> GenreLoadingScreen(modifier = Modifier.fillMaxSize())
            is MovieUiState.Error -> GenreErrorScreen(modifier = Modifier.fillMaxSize())
            is MovieUiState.Success -> GenreSuccessScreen(movieList = uiState.movies, modifier = Modifier
                .fillMaxSize()
                .padding(it))
        }
    }
}

@Composable
fun GenreSuccessScreen(
    movieList: List<Movie>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .padding(20.dp)
    ) {
        items(movieList, key = { it.id }) { movie ->
            MovieItem(movie = movie)
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    var showModalMovie by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_small)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = movie.title,
            modifier = Modifier
                .height(minOf(20.dp, 50.dp))
        )
        Card(
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier
                .clickable { showModalMovie = true }
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(Constants.BASE_URL_IMAGE + movie.poster)
                    .crossfade(true)
                    .size(700)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        MovieModal(showModal = showModalMovie, onDismiss = { showModalMovie = false }, movie = movie)
    }
}

@Composable
fun GenreLoadingScreen(
    modifier: Modifier = Modifier
) {

}

@Composable
fun GenreErrorScreen(
    modifier: Modifier = Modifier
) {

}