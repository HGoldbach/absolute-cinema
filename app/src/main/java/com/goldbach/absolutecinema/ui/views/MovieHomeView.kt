package com.goldbach.absolutecinema.ui.views

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.dto.MovieDto
import com.goldbach.absolutecinema.ui.AppViewModelProvider
import com.goldbach.absolutecinema.ui.MovieBottomAppBar
import com.goldbach.absolutecinema.ui.components.ErrorGenre
import com.goldbach.absolutecinema.ui.components.LoadingGenre
import com.goldbach.absolutecinema.ui.components.MovieModal
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme
import com.goldbach.absolutecinema.ui.viewmodels.HomeViewModel
import com.goldbach.absolutecinema.ui.viewmodels.MovieUiState
import kotlinx.coroutines.launch

object MovieHomeDestination : NavigationDestination {
    override val route = "movie_home"
    override val title = "Movie Home"
}

@Composable
fun MovieHomeView(
    navigateToMovies: () -> Unit,
    navigateToSeries: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val movieUiState: MovieUiState = viewModel.movieUiState
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        bottomBar = {
            MovieBottomAppBar(
                navigateToMovies = navigateToMovies,
                navigateToSeries = navigateToSeries,
                navigateToSearch = navigateToSearch,
                navigateToProfile = navigateToProfile,
                currentlyRoute = MovieHomeDestination.title
            )
        }
    ) {
        when (movieUiState) {
            is MovieUiState.Loading -> LoadingGenre(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
            )

            is MovieUiState.Error -> ErrorGenre(
                retryAction = {},
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
            )

            is MovieUiState.Success -> HomeScreen(
                moviesList = movieUiState.movies,
                seriesList = movieUiState.series,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.saveMovie(it)
                    }
                },
                modifier = modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun HomeScreen(
    moviesList: List<MovieDto>,
    seriesList: List<MovieDto>,
    onSaveClick: (MovieDto) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colorScheme.tertiary
        )
        HomeSection(title = R.string.recently_released_movies) {
            ReleasedMovies(moviesList = moviesList, onSaveClick = onSaveClick)
        }
        HomeSection(
            title = R.string.popular_tv_shows,
            modifier = Modifier.padding(bottom = 100.dp)
        ) {
            PopularShows(showsList = seriesList, onSaveClick = onSaveClick)
        }
    }
}

@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}


@Composable
fun ReleasedMovies(
    moviesList: List<MovieDto>,
    onSaveClick: (MovieDto) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items = moviesList, key = { it.id }) { movie ->
            HomeMoviesShowsItem(
                movie = movie,
                onSaveClick = onSaveClick,
                modifier = Modifier
                    .fillMaxSize()
                    .width(125.dp)
            )
        }
    }
}

@Composable
fun PopularShows(
    showsList: List<MovieDto>,
    onSaveClick: (MovieDto) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(items = showsList, key = { it.id }) { show ->
            HomeMoviesShowsItem(
                movie = show,
                onSaveClick = onSaveClick,
                modifier = Modifier
                    .fillMaxSize()
                    .width(125.dp)
            )
        }
    }
}

@Composable
fun HomeMoviesShowsItem(
    movie: MovieDto,
    onSaveClick: (MovieDto) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showModalMovie by remember {
        mutableStateOf(false)
    }
    ItemCard(
        onCardClick = { showModalMovie = true },
        movie,
        modifier = modifier
    )
    MovieModal(
        showModal = showModalMovie,
        onDismiss = { showModalMovie = false },
        onSave = onSaveClick,
        movie = movie
    )
}

@Composable
fun ItemCard(
    onCardClick: () -> Unit,
    movie: MovieDto,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(
                vertical = 8.dp,
                horizontal = 4.dp
            )
            .clickable { onCardClick() },
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
}


@Preview(showBackground = true, heightDp = 200, widthDp = 120)
@Composable
private fun ItemCardPreview() {
    AbsoluteCinemaTheme {
        ItemCard(
            movie = MovieDto("", "", "", "", ""),
            onCardClick = {}
        )
    }
}

@Preview(showBackground = true,heightDp = 400, widthDp = 240)
@Composable
private fun HomeMoviesShowsItemPreview() {
    AbsoluteCinemaTheme {
        HomeMoviesShowsItem(movie = MovieDto("", "Pirates of Caribbean", "Description of Pirates of Caribbean", "", ""), onSaveClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ReleasedMoviesPreview() {
    AbsoluteCinemaTheme {
        val mockData = List(10) { MovieDto("$it", "", "", "", "") }
        ReleasedMovies(moviesList = mockData, onSaveClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PopularShowsPreview() {
    AbsoluteCinemaTheme {
        val mockData = List(10) { MovieDto("$it", "", "", "", "") }
        PopularShows(showsList = mockData, onSaveClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSectionPreview() {
    AbsoluteCinemaTheme {
        HomeSection(title = R.string.recently_released_movies) {
            val mockData = List(10) { MovieDto("$it", "", "", "", "") }
            ReleasedMovies(moviesList = mockData, onSaveClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    AbsoluteCinemaTheme {
        val mockData = List(10) { MovieDto("$it", "", "", "", "") }
        HomeScreen(
            moviesList = mockData,
            seriesList = mockData,
            onSaveClick = {}
        )
    }
}

