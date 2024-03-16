package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.data.dto.MovieDto
import com.goldbach.absolutecinema.ui.AppViewModelProvider
import com.goldbach.absolutecinema.ui.MovieBottomAppBar
import com.goldbach.absolutecinema.ui.components.MovieItem
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme
import com.goldbach.absolutecinema.ui.viewmodels.SearchViewModel
import kotlinx.coroutines.launch

object SearchDestination : NavigationDestination {
    override val route = "search"
    override val title = "Search"
}

@Composable
fun SearchView(
    navigateToHome: () -> Unit,
    navigateToMovie: () -> Unit,
    navigateToSeries: () -> Unit,
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val searchText by viewModel.searchText.collectAsState()
    val movies by viewModel.results.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        bottomBar = {
            MovieBottomAppBar(
                navigateToHome = navigateToHome,
                navigateToMovies = navigateToMovie,
                navigateToSeries = navigateToSeries,
                navigateToProfile = navigateToProfile,
                currentlyRoute = SearchDestination.title
            )
        }
    ) {
        SearchScreen(
            searchText = searchText,
            onSearchChange = { text ->
                coroutineScope.launch {
                    viewModel.onSearchTextChange(text)
                }
            },
            searchMovies = { viewModel.searchMovies(searchText) },
            onSaveClick = { movie ->
                coroutineScope.launch {
                    viewModel.saveMovie(movie)
                }
            },
            movies = movies,
            modifier = modifier.padding(it)
        )
    }
}

@Composable
fun SearchScreen(
    searchText: String,
    onSearchChange: (String) -> Unit,
    searchMovies: () -> Unit,
    onSaveClick: (MovieDto) -> Unit,
    movies: List<MovieDto>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Search Movies/Shows",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .paddingFromBaseline(top = 50.dp, bottom = 20.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        SearchBar(
            searchText = searchText,
            onSearchChange = onSearchChange,
            searchMovies = searchMovies,
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 20.dp
            )
        )
        if (movies.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No movies/series found", style = MaterialTheme.typography.labelSmall)
                Text(text = "Try adjusting your search", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            MoviesShowsGrid(
                movies = movies,
                onSaveClick = onSaveClick
            )
        }
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onSearchChange: (String) -> Unit,
    searchMovies: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = searchText,
        onValueChange = {
            onSearchChange(it)
            searchMovies()
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(text = stringResource(R.string.placeholder_search))
        },
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
fun MoviesShowsGrid(
    movies: List<MovieDto>,
    onSaveClick: (MovieDto) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 5.dp
            )
    ) {
        movies.filter { movie ->
            movie.description != null
        }.let {  movies ->
            items(items = movies) { movie ->
                MovieItem(movie = movie, onSaveClick = { onSaveClick(it) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    AbsoluteCinemaTheme {
        SearchBar(searchText = "", onSearchChange = {}, searchMovies = {}, Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun MoviesShowsGridPreview() {
    AbsoluteCinemaTheme {
        val mockData = List(10) { MovieDto("$it", "$it - Movie", "", "", "") }
        MoviesShowsGrid(
            movies = mockData,
            onSaveClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    AbsoluteCinemaTheme {
        val mockData = List(10) { MovieDto("$it", "$it - Movie", "", "", "") }
        SearchScreen(
            searchText = "",
            onSearchChange = {},
            searchMovies = { /*TODO*/ },
            onSaveClick = {},
            movies = mockData
        )
    }
}


