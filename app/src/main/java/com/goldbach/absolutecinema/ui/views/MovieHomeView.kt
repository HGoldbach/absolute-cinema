package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.goldbach.absolutecinema.R
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination

object MovieHomeDestination : NavigationDestination {
    override val route = "movie_home"
    override val title = "Movie Home"
}

@Composable
fun MovieHomeView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.app_name))

    }

}