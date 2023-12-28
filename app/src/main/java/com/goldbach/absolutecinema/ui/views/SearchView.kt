package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.ui.AppViewModelProvider
import com.goldbach.absolutecinema.ui.MovieBottomAppBar
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination
import com.goldbach.absolutecinema.ui.viewmodels.MovieUiState
import com.goldbach.absolutecinema.ui.viewmodels.SearchViewModel

object SearchDestination : NavigationDestination {
    override val route = "search"
    override val title = "Search"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    navigateToHome: () -> Unit,
    navigateToMovie: () -> Unit,
    navigateToSeries: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val isActiveSearch by viewModel.isActiveSearch.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val movies by viewModel.results.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Scaffold(
        bottomBar = {
            MovieBottomAppBar(
                navigateToHome = navigateToHome,
                navigateToMovies = navigateToMovie,
                navigateToSeries = navigateToSeries,
                currentlyRoute = SearchDestination.title
            )
        }
    ) {
        Column(
            modifier = modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                query = searchText,
                onQueryChange = viewModel::onSearchTextChange,
                onSearch = {
                    viewModel.searchMovies(searchText)
                    viewModel.changeActiveSearch()
                },
                active = isActiveSearch,
                onActiveChange = { viewModel.changeActiveSearch() },
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                ),
                tonalElevation = 0.dp,
                placeholder = { Text(text = "Search") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    if (isActiveSearch) {
                        Icon(
                            modifier = Modifier.clickable {
                                if (searchText.isNotEmpty()) {
                                    viewModel.onSearchTextChange("")
                                } else {
                                    viewModel.changeActiveSearch()
                                }
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon"
                        )
                    }
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(20.dp)
            ) {}
            Spacer(modifier = Modifier.height(16.dp))
            if(movies.isEmpty()) {
                Text(text = "No movies/series found", style = MaterialTheme.typography.labelSmall)
                Text(text = "Try adjusting your search", style = MaterialTheme.typography.bodyLarge)
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(items = movies) { movie ->
                   MovieItem(movie = movie) 
                }
            }
        }


        /*
        when(uiState) {
            is MovieUiState.Loading -> MenuLoadingScreen(modifier = Modifier.padding(it))
            is MovieUiState.Error -> MenuErrorScreen(modifier = Modifier.padding(it))
            is MovieUiState.Success -> SearchViewBody()

            is MovieUiState.Success -> GenreSuccessScreen(
                movieList = uiState.movies,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )


        }


        SearchViewBody(
            modifier = Modifier.padding(it)
        )


         */

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchViewBody(
    modifier: Modifier = Modifier
) {

}
