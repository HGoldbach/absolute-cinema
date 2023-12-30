package com.goldbach.absolutecinema.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.dto.MovieDto
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme

@Composable
fun MovieModal(
    modifier: Modifier = Modifier,
    showModal: Boolean = false,
    onDismiss: () -> Unit,
    movie: MovieDto
) {
    if (showModal) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
        ) {
            Card(
                modifier = modifier,
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                MovieModalBody(movie = movie, onClosePressed = onDismiss)
            }
        }
    }
}

@Composable
fun MovieModalBody(
    movie: MovieDto,
    onClosePressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieModalDetails(movie = movie, onClosePressed = onClosePressed)
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
           Text(
               text = "Add to list",
               style = MaterialTheme.typography.labelSmall
           )
        }
    }
}

@Composable
fun MovieModalDetails(
    movie: MovieDto,
    onClosePressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close icon",
            modifier = Modifier
                .size(30.dp)
                .clickable { onClosePressed() },
            tint = MaterialTheme.colorScheme.error,
        )
    }
    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(8.dp),
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
            modifier = Modifier.size(300.dp, 300.dp)
        )
    }
    Text(
        text = movie.description,
        textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
    )
    Text(
        text = "Released Date: ${movie.releaseDate}",
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
    )
}

@Preview(showBackground = true)
@Composable
fun MovieModalPreview() {
    AbsoluteCinemaTheme {
        MovieModal(
            showModal = true,
            movie = MovieDto("1", "Aquaman", "Some text", "", ""),
            onDismiss = {})
    }
}