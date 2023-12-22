package com.goldbach.absolutecinema.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.goldbach.absolutecinema.ui.views.HomeView
import com.goldbach.absolutecinema.ui.views.HomeViewDestination
import com.goldbach.absolutecinema.ui.views.MovieGenreDestination
import com.goldbach.absolutecinema.ui.views.MovieGenreView
import com.goldbach.absolutecinema.ui.views.MovieHomeDestination
import com.goldbach.absolutecinema.ui.views.MovieHomeView
import com.goldbach.absolutecinema.ui.views.MovieMenuDestination
import com.goldbach.absolutecinema.ui.views.MovieMenuView

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
        composable(route = MovieHomeDestination.route) {
            MovieHomeView(
                navigateToMovie = { navController.navigate(MovieMenuDestination.route) }
            )
        }
        composable(route = HomeViewDestination.route) {
            HomeView(
                retryAction = { /*TODO*/ },
                navigateUp = { navController.navigateUp() }
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
            )
        }
        composable(route = MovieMenuDestination.route) {
            MovieMenuView(
                navigateUp = { navController.navigateUp() },
                navigateToGenreSelected = { navController.navigate("${MovieGenreDestination.route}/$it") }
            )
        }


    }
}