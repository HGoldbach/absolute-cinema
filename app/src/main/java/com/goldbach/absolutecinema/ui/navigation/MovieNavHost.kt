package com.goldbach.absolutecinema.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.goldbach.absolutecinema.ui.views.MovieGenreDestination
import com.goldbach.absolutecinema.ui.views.MovieGenreView
import com.goldbach.absolutecinema.ui.views.MovieHomeDestination
import com.goldbach.absolutecinema.ui.views.MovieHomeView
import com.goldbach.absolutecinema.ui.views.MovieMenuDestination
import com.goldbach.absolutecinema.ui.views.MovieMenuView
import com.goldbach.absolutecinema.ui.views.SearchDestination
import com.goldbach.absolutecinema.ui.views.SearchView
import com.goldbach.absolutecinema.ui.views.SerieGenreDestination
import com.goldbach.absolutecinema.ui.views.SerieGenreView
import com.goldbach.absolutecinema.ui.views.SerieMenuDestination
import com.goldbach.absolutecinema.ui.views.SerieMenuView

@Composable
fun MovieNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MovieHomeDestination.route,
        modifier = modifier
    ) {

        // Home
        composable(route = MovieHomeDestination.route) {
            MovieHomeView(
                navigateToMovies = { navController.navigate(MovieMenuDestination.route) },
                navigateToSeries = { navController.navigate(SerieMenuDestination.route) },
                navigateToSearch = { navController.navigate(SearchDestination.route) }
            )
        }

        // Movies
        composable(route = MovieMenuDestination.route) {
            MovieMenuView(
                navigateUp = { navController.navigateUp() },
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToSeries = { navController.navigate(SerieMenuDestination.route) },
                navigateToSearch = { navController.navigate(SearchDestination.route) },
                navigateToGenreSelected = { navController.navigate("${MovieGenreDestination.route}/$it") }
            )
        }
        composable(
            route = MovieGenreDestination.routeWithArgs,
            arguments = listOf(navArgument(MovieGenreDestination.genreIdArg) {
                type = NavType.IntType
            })
        ) {
            MovieGenreView(
                navigateUp = { navController.navigateUp() },
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToSeries = { navController.navigate(SerieMenuDestination.route) },
                navigateToSearch = { navController.navigate(SearchDestination.route) }
            )
        }

        // Series
        composable(route = SerieMenuDestination.route) {
            SerieMenuView(
                navigateUp = { navController.navigateUp() },
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToMovies = { navController.navigate(MovieMenuDestination.route) },
                navigateToSearch = { navController.navigate(SearchDestination.route) },
                navigateToGenreSelected = { navController.navigate("${SerieGenreDestination.route}/${it}") }
            )
        }
        composable(
            route = SerieGenreDestination.routeWithArgs,
            arguments = listOf(navArgument(SerieGenreDestination.genreIdArg) {
                type = NavType.IntType
            })
        ) {
            SerieGenreView(
                navigateUp = { navController.navigateUp() },
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToMovies = { navController.navigate(MovieMenuDestination.route) },
                navigateToSearch = { navController.navigate(SearchDestination.route) }
            )
        }

        // Search
        composable(route = SearchDestination.route) {
            SearchView(
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToMovie = { navController.navigate(MovieMenuDestination.route) },
                navigateToSeries = { navController.navigate(SerieMenuDestination.route) },
            )
        }


    }
}