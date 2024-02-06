package com.goldbach.absolutecinema.ui.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goldbach.absolutecinema.ui.MovieBottomAppBar
import com.goldbach.absolutecinema.ui.navigation.NavigationDestination
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme

object ProfileMenuDestination : NavigationDestination {
    override val route = "profile_menu"
    override val title = "Profile Menu"
}

@Composable
fun ProfileMenuView(
    navigateToHome: () -> Unit,
    navigateToMovie: () -> Unit,
    navigateToSeries: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToProfileList: () -> Unit,
    navigateToProfileFavorites: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            MovieBottomAppBar(
                navigateToHome = navigateToHome,
                navigateToMovies = navigateToMovie,
                navigateToSeries = navigateToSeries,
                navigateToSearch = navigateToSearch,
                currentlyRoute = ProfileMenuDestination.title
            )
        }
    ) {
        ProfileMenuBody(
            modifier = Modifier.padding(it),
            navigateToProfileList = navigateToProfileList,
            navigateToProfileFavorites = navigateToProfileFavorites
        )
    }
}

@Composable
fun ProfileMenuBody(
    navigateToProfileList: () -> Unit,
    navigateToProfileFavorites: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        ProfileMenuItem(
            title = "List",
            info = "My list of movies and series",
            icon = Icons.Outlined.List,
            onItemClick = navigateToProfileList
        )
        ProfileMenuItem(
            title = "Favorites",
            info = "My favorite ones",
            icon = Icons.Outlined.Favorite,
            onItemClick = navigateToProfileFavorites
        )
    }
}

@Composable
fun ProfileMenuItem(
    title: String,
    info: String,
    icon: ImageVector,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onItemClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "List icon",
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 60.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = info,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowRight,
            contentDescription = "Arrow right icon"
        )
    }
    Divider(
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier
            .height(.3.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileMenuViewPreview() {
    AbsoluteCinemaTheme {
        ProfileMenuView(
            navigateToHome = { /*TODO*/ },
            navigateToMovie = { /*TODO*/ },
            navigateToSeries = { /*TODO*/ },
            navigateToSearch = { /*TODO*/ },
            navigateToProfileList = {},
            navigateToProfileFavorites = {}
        )
    }
}