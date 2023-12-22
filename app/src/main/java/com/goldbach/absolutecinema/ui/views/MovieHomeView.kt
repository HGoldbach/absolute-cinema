package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme

object MovieHomeDestination : NavigationDestination {
    override val route = "movie_home"
    override val title = "Movie Home"
}

@Composable
fun MovieHomeView(
    navigateToMovie: () -> Unit,
    navigateToSeries: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.tertiaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(bottom = 50.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
        ElevatedButton(
            onClick = navigateToMovie,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_extra_large),
                    end = dimensionResource(id = R.dimen.padding_extra_large)
                ),
            shape = RoundedCornerShape(
                topStart = 5.dp,
                topEnd = 5.dp
            ),
        ) {
            Text(
                text = "Movies",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
        ElevatedButton(
            onClick = navigateToSeries,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_extra_large),
                    end = dimensionResource(id = R.dimen.padding_extra_large)
                ),
            shape = RoundedCornerShape(
                bottomStart = 5.dp,
                bottomEnd = 5.dp
            )
        ) {
            Text(
                text = "TV Series",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MovieHomeViewPreview() {
    AbsoluteCinemaTheme {
        MovieHomeView({},{})
    }
}