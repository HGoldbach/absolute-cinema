package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
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
import com.goldbach.absolutecinema.ui.components.ErrorGenre
import com.goldbach.absolutecinema.ui.components.LoadingGenre
import com.goldbach.absolutecinema.ui.components.MovieModal
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
    navigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val movieUiState: MovieUiState = viewModel.movieUiState
    Scaffold(
        bottomBar = {
            MovieBottomAppBar(
                navigateToMovies = navigateToMovies,
                navigateToSeries = navigateToSeries,
                navigateToSearch = navigateToSearch,
                currentlyRoute = MovieHomeDestination.title
            )
        }
    ) {
        when (movieUiState) {
            is MovieUiState.Loading -> LoadingGenre(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )

            is MovieUiState.Error -> ErrorGenre(
                retryAction = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )

            is MovieUiState.Success -> HomeBodyScreen(
                moviesList = movieUiState.movies,
                seriesList = movieUiState.series,
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
            HomeMoviesList(
                moviesList = moviesList,
                seriesList = seriesList,
            )
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
                        HomeMoviesItem(
                            movie = movie,
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(125.dp)
                        )
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
                        HomeMoviesItem(
                            movie = series,
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(125.dp)
                        )
                    }
                }
            }
        }
    }


}

@Composable
fun HomeMoviesItem(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    var showModalMovie by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = modifier
            .padding(
                vertical = 8.dp,
                horizontal = 4.dp
            )
            .clickable { showModalMovie = true },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(Constants.BASE_URL_IMAGE + movie.poster)
                .crossfade(true)
                .build(),
            contentDescription = movie.title,
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.ic_broken_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
    MovieModal(
        showModal = showModalMovie, onDismiss = { showModalMovie = false }, movie = movie
    )


}

@Preview(showBackground = true)
@Composable
fun HomeMoviesListPreview() {
    AbsoluteCinemaTheme {
        val mockData = List(10) { Movie("$it", "", "", "", "") }
        HomeMoviesList(moviesList = mockData, seriesList = mockData)
    }
}