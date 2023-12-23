package com.goldbach.absolutecinema.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.data.Constants
import com.goldbach.absolutecinema.data.models.Movie
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme

@Composable
fun MovieModal(
    modifier: Modifier = Modifier,
    showModal: Boolean = false,
    onDismiss: () -> Unit,
    movie: Movie
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
                Column(
                    modifier = modifier
                        .padding(dimensionResource(id = R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                            modifier = Modifier
                                .width(250.dp)
                        )
                    }
                    Text(
                        text = "Title: ${movie.title}"
                    )
                    Text(
                        text = "Description: ${movie.description}"
                    )
                    Text(
                        text = "Released Date: ${movie.releaseDate} "
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieModalPreview() {
    AbsoluteCinemaTheme {
        MovieModal(
            showModal = true,
            movie = Movie("1", "Aquaman", "Some text", "", ""),
            onDismiss = {})
    }
}