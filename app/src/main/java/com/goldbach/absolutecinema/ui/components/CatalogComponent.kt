package com.goldbach.absolutecinema.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.models.Movie
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme

@Composable
fun SuccessCatalogGrid(
    movieList: List<Movie>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .padding(20.dp)
    ) {
        items(movieList, key = { it.id }) { movie ->
            MovieItem(
                movie = movie,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(125.dp, 300.dp)
            )
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
                    .size(125, 200)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                modifier = Modifier.fillMaxWidth()
            )
        }
        MovieModal(
            showModal = showModalMovie,
            onDismiss = { showModalMovie = false },
            movie = movie
        )
    }
}

@Composable
fun ErrorCatalog(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
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
        Text(
            text = "Failed to load",
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
        Button(onClick = retryAction) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun LoadingCatalog(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = ""
    )
}


// Previews
@Preview(showBackground = true)
@Composable
fun SuccessCatalogGridPreview() {
    AbsoluteCinemaTheme {
        val mockData = List(10) { Movie("$it", "", "", "", "") }
        SuccessCatalogGrid(movieList = mockData)
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorCatalogPreview() {
    AbsoluteCinemaTheme {
        ErrorCatalog(retryAction = { /*TODO*/ })
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingCatalogPreview() {
    AbsoluteCinemaTheme {
        LoadingCatalog()
    }
}