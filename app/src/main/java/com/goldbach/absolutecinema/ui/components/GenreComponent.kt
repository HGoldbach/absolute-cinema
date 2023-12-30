package com.goldbach.absolutecinema.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.data.models.Genre
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme

@Composable
fun SuccessGenreGrid(
    genres: List<Genre>,
    navigateToGenreSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        items(genres) { genre ->
            GenreItem(
                genre = genre,
                modifier = Modifier,
                onItemClick = { navigateToGenreSelected(genre.id) }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreItem(
    genre: Genre,
    onItemClick: (Genre) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_small)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiary
        ),
        onClick = { onItemClick(genre) }
    ) {
        Text(
            text = genre.name,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ErrorGenre(
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
fun LoadingGenre(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = "Loading"
    )
}


// Previews
@Preview(showBackground = true)
@Composable
fun SuccessGenreGridPreview() {
    AbsoluteCinemaTheme {
        SuccessGenreGrid(
            genres = listOf(Genre(1, "Horror"), Genre(2, "Thriller")),
            navigateToGenreSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GenreItemPreview() {
    AbsoluteCinemaTheme {
        GenreItem(genre = Genre(1, "Western"), onItemClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorGenrePreview() {
    AbsoluteCinemaTheme {
        ErrorGenre(retryAction = { /*TODO*/ })
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingGenrePreview() {
    AbsoluteCinemaTheme {
        LoadingGenre()
    }
}