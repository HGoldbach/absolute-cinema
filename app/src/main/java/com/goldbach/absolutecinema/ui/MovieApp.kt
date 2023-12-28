package com.goldbach.absolutecinema.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Theaters
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.goldbach.absolutecinema.ui.navigation.MovieNavHost
import com.goldbach.absolutecinema.ui.theme.AbsoluteCinemaTheme
import com.goldbach.absolutecinema.ui.views.MovieHomeDestination
import com.goldbach.absolutecinema.ui.views.MovieMenuDestination
import com.goldbach.absolutecinema.ui.views.SearchDestination
import com.goldbach.absolutecinema.ui.views.SerieMenuDestination

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val items = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavigationItem(
        title = "Search",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    ),
    BottomNavigationItem(
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp(navController: NavHostController = rememberNavController()) {
    MovieNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title)},
        scrollBehavior = scrollBehavior,
        modifier = modifier,
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}



@Composable
fun MovieBottomAppBar(
    navigateToHome: () -> Unit = {},
    navigateToMovies: () -> Unit = {},
    navigateToSeries: () -> Unit = {},
    navigateToSearch: () -> Unit = {},
    currentlyRoute: String
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentlyRoute == MovieHomeDestination.title,
            onClick = {
                navigateToHome()
            },
            icon = {
                Icon(
                    imageVector = if (currentlyRoute == MovieHomeDestination.title) {
                        Icons.Filled.Home
                    } else Icons.Outlined.Home,
                    contentDescription = MovieHomeDestination.title
                )
            },
            label = {
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        )
        NavigationBarItem(
            selected = currentlyRoute == MovieMenuDestination.title,
            onClick = {
                navigateToMovies()
            },
            icon = {
                Icon(
                    imageVector = if (currentlyRoute == MovieMenuDestination.title) {
                        Icons.Filled.Movie
                    } else Icons.Outlined.Movie,
                    contentDescription = MovieMenuDestination.title
                )
            },
            label = {
                Text(
                    text = "Movies",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
        NavigationBarItem(
            selected = currentlyRoute == SerieMenuDestination.title,
            onClick = {
                navigateToSeries()
            },
            icon = {
                Icon(
                    imageVector = if (currentlyRoute == SerieMenuDestination.title) {
                        Icons.Filled.Theaters
                    } else Icons.Outlined.Theaters,
                    contentDescription = SerieMenuDestination.title
                )
            },
            label = {
                Text(
                    text = "Series",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
        NavigationBarItem(
            selected = currentlyRoute == SearchDestination.title,
            onClick = {
                navigateToSearch()
            },
            icon = {
                Icon(
                    imageVector = if (currentlyRoute == SearchDestination.title) {
                        Icons.Filled.Search
                    } else Icons.Outlined.Search,
                    contentDescription = SearchDestination.title
                )
            },
            label = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MovieTopAppBarPreview() {
    AbsoluteCinemaTheme {
        MovieTopAppBar(title = "Teste", canNavigateBack = true) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieBottomAppBarPreview() {
    AbsoluteCinemaTheme {
        MovieBottomAppBar({},{},{},{},"Movie Genres")
    }
}