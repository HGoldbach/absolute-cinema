package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.models.Movie
import com.goldbach.absolutecinema.ui.AppViewModelProvider
import com.goldbach.absolutecinema.ui.MovieBottomAppBar
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme
import com.goldbach.absolutecinema.ui.viewmodels.HomeViewModel
import com.goldbach.absolutecinema.ui.viewmodels.MovieUiState

object MovieHomeDestination : NavigationDestination {
    override val route = "movie_home"
    override val title = "Movie Home"
}

@Composable
fun MovieHomeView(
    navigateToMovies: () -> Unit,
    navigateToSeries: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val movieUiState: MovieUiState = viewModel.movieUiState
    val serieUiState: MovieUiState = viewModel.serieUiState
    Scaffold(
        bottomBar = {
            MovieBottomAppBar(
                navigateToMovies = navigateToMovies,
                navigateToSeries = navigateToSeries,
                navigateToSearch = { /*TODO*/ },
                currentlyRoute = MovieHomeDestination.title
            )
        }
    ) {
        when {
            movieUiState is MovieUiState.Loading && serieUiState is MovieUiState.Loading -> MenuLoadingScreen(
                modifier = Modifier.padding(it)
            )

            movieUiState is MovieUiState.Error && serieUiState is MovieUiState.Error -> MenuErrorScreen(
                modifier = Modifier.padding(it)
            )

            movieUiState is MovieUiState.Success && serieUiState is MovieUiState.Success -> HomeBodyScreen(
                moviesList = movieUiState.movies,
                seriesList = serieUiState.movies,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun HomeBodyScreen(
    moviesList: List<Movie>,
    seriesList: List<Movie>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colorScheme.tertiary
        )
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            HomeMoviesList(moviesList = moviesList, seriesList = seriesList)
        }
    }
}

@Composable
fun HomeMoviesList(
    moviesList: List<Movie>,
    seriesList: List<Movie>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Recently Released Movies",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelSmall
            )
            Column() {
                LazyHorizontalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    rows = GridCells.Fixed(1)
                ) {
                    items(items = moviesList, key = { it.id }) { movie ->
                        HomeMoviesItem(movie = movie)
                    }
                }
            }
        }
        item {
            Text(
                text = "Popular TV Series",
                modifier = Modifier
                    .padding(top = 20.dp, start = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelSmall
            )
            Column() {
                LazyHorizontalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    rows = GridCells.Fixed(1)
                ) {
                    items(items = seriesList, key = { it.id }) { series ->
                        HomeMoviesItem(movie = series)
                    }
                }
            }
        }
    }


}

@Composable
fun HomeMoviesItem(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(
            vertical = 8.dp,
            horizontal = 4.dp
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(Constants.BASE_URL_IMAGE + movie.poster)
                .crossfade(true)
                .size(600)
                .build(),
            contentDescription = movie.title
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieHomeViewPreview() {
    AbsoluteCinemaTheme {
        MovieHomeView({}, {})
    }
}