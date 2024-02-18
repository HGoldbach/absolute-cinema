package com.goldbach.absolutecinema.ui.components

import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.dto.MovieDto
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieModal(
    modifier: Modifier = Modifier,
    showModal: Boolean = false,
    onDismiss: () -> Unit,
    onSave: (MovieDto) -> Unit,
    movie: MovieDto
) {
    if (showModal) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
        ) {
            MovieModalBody(movie = movie, onClosePressed = onDismiss, onSave = onSave)
        }
    }
}

@Composable
fun MovieModalBody(
    movie: MovieDto,
    onSave: (MovieDto) -> Unit,
    onClosePressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieModalDetails(movie = movie, onClosePressed = onClosePressed)
        Button(
            onClick = {
                onSave(movie)
                Toast.makeText(
                    context,
                    "Movie added to your list!",
                    Toast.LENGTH_SHORT
                ).show()
                onClosePressed()
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
        ) {
            Text(
                text = "Add to list", style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun MovieModalDetails(
    movie: MovieDto, onClosePressed: () -> Unit, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = movie.title,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        )
    }
    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(8.dp),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(Constants.BASE_URL_IMAGE + movie.poster).crossfade(true).build(),
            contentDescription = movie.title,
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.ic_broken_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(300.dp, 350.dp)
        )
    }
    Text(
        text = movie.description,
        textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.displaySmall,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
    )
}

@Preview(showBackground = true)
@Composable
fun MovieModalPreview() {
    AbsoluteCinemaTheme {
        MovieModalBody(
            movie = MovieDto("1", "Aquaman", "Some text", "", ""),
            onClosePressed = {},
            onSave = {},
        )
    }
}