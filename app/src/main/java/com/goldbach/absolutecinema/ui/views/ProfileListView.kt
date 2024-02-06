package com.goldbach.absolutecinema.ui.views

import android.text.Spannable.Factory
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.dto.MovieDto
import com.goldbach.absolutecinema.data.models.Movie
import com.goldbach.absolutecinema.ui.AppViewModelProvider
import com.goldbach.absolutecinema.ui.MovieBottomAppBar
import com.goldbach.absolutecinema.ui.components.SuccessCatalogGrid
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination
import com.goldbach.absolutecinema.ui.viewmodels.ProfileListViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ProfileListDestination : NavigationDestination {
    override val route = "profile_list"
    override val title = "profile_list"
}

@Composable
fun ProfileListView(
    navigateToHome: () -> Unit,
    navigateToMovies: () -> Unit,
    navigateToSeries: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToProfile: () -> Unit,
    viewModel: ProfileListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.profileUiState.collectAsState()
    Scaffold(
        bottomBar = {
            MovieBottomAppBar(
                navigateToHome = navigateToHome,
                navigateToMovies = navigateToMovies,
                navigateToSeries = navigateToSeries,
                navigateToSearch = navigateToSearch,
                navigateToProfile = navigateToProfile,
                currentlyRoute = ProfileListDestination.title
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
                    viewModel.setMovieToFavorites(it)
                }
            },
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun ListGrid(
    moviesList: List<MovieDto>,
    onRemove: (MovieDto) -> Unit,
    onFavorite: (MovieDto) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(20.dp)
    ) {
        items(moviesList, key = { it.id }) { movie ->
            MovieItem(
                movie = movie,
                onRemove = onRemove,
                onFavorite = onFavorite
            )
        }
    }
}

@Composable
fun MovieItem(
    movie: MovieDto,
    onRemove: (MovieDto) -> Unit,
    onFavorite: (MovieDto) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFavorite by remember {
        mutableStateOf(movie.isFavorite)
    }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_small)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Card(
                elevation = CardDefaults.cardElevation(6.dp),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(Constants.BASE_URL_IMAGE + movie.poster)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    modifier = Modifier.fillMaxWidth().height(235.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .height(235.dp)
                    .padding(5.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color.hsl(0F, 0F, 0F, 0.5f), RoundedCornerShape(25.dp))
                        .padding(3.dp)
                        .clickable {
                            onRemove(movie)
                        },
                    tint = Color.hsl(0F, 1f, 1f)
                )
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = "",
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            isFavorite = !isFavorite
                            coroutineScope.launch {
                                delay(500)
                                onFavorite(movie)
                            }
                        },
                    tint = Color.hsl(347f, 1f, 0.6f)
                )
            }
        }

    }
}

