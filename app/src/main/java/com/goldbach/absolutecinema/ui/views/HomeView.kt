package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.models.Movie
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme
import com.goldbach.absolutecinema.ui.viewmodels.HomeViewModel
import com.goldbach.absolutecinema.ui.viewmodels.MovieUiState

@Composable
fun HomeView(
    movieUiState: MovieUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
   when(movieUiState) {
       is MovieUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
       is MovieUiState.Success -> SuccessScreen(movieUiState.movies, modifier)
       is MovieUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
   }
}

@Composable
fun SuccessScreen(
    movies: List<Movie>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
    ) {
        items(items = movies, key = { it.id }) {movie ->
            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.size(10.dp))
                Row {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(Constants.BASE_URL_IMAGE + movie.poster)
                            .crossfade(true)
                            .size(700)
                            .build(),
                        error = painterResource(id = R.drawable.ic_broken_image),
                        placeholder = painterResource(id = R.drawable.loading_img),
                        contentDescription = "${movie.title} poster",

                    )
                    Column(
                        modifier = modifier
                            .padding(start = 8.dp, end = 8.dp)
                    ) {
                        Text(
                            text = movie.description,
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessScreenPreview() {
    AbsoluteCinemaTheme {
        SuccessScreen(movies = listOf(
            Movie(
                "1",
                "Wonka",
                "Willy Wonka – chock-full of ideas and determined to change the world one delectable bite at a time – is proof that the best things in life begin with a dream, and if you’re lucky enough to meet Willy Wonka, anything is possible.",
                "https://image.tmdb.org/t/p/w500//qhb1qOilapbapxWQn9jtRCMwXJF.jpg",
                "2023-12-06"
            )
        ))
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.loading_img), 
        contentDescription = "Loading",
        modifier = modifier.size(200.dp)
    )
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), 
            contentDescription = ""
        )
        Text(text = "Failed to load", modifier = Modifier.padding(16.dp))
    }
}