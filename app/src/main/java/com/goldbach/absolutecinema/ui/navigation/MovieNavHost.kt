package com.goldbach.absolutecinema.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.goldbach.absolutecinema.ui.views.MovieCatalogDestination
import com.goldbach.absolutecinema.ui.views.MovieGenreView
import com.goldbach.absolutecinema.ui.views.MovieHomeDestination
import com.goldbach.absolutecinema.ui.views.MovieHomeView
import com.goldbach.absolutecinema.ui.views.MovieMenuDestination
import com.goldbach.absolutecinema.ui.views.MovieMenuView
import com.goldbach.absolutecinema.ui.views.ProfileFavoritesDestination
import com.goldbach.absolutecinema.ui.views.ProfileFavoritesView
import com.goldbach.absolutecinema.ui.views.ProfileListDestination
import com.goldbach.absolutecinema.ui.views.ProfileListView
import com.goldbach.absolutecinema.ui.views.ProfileMenuDestination
import com.goldbach.absolutecinema.ui.views.ProfileMenuView
import com.goldbach.absolutecinema.ui.views.SearchDestination
import com.goldbach.absolutecinema.ui.views.SearchView
import com.goldbach.absolutecinema.ui.views.SerieCatalogDestination
import com.goldbach.absolutecinema.ui.views.SerieCatalogView
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
                navigateToSearch = { navController.navigate(SearchDestination.route) },
                navigateToProfile = { navController.navigate(ProfileMenuDestination.route) }
            )
        }

        // Movies
        composable(route = MovieMenuDestination.route) {
            MovieMenuView(
                navigateUp = { navController.navigateUp() },
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToSeries = { navController.navigate(SerieMenuDestination.route) },
                navigateToSearch = { navController.navigate(SearchDestination.route) },
                navigateToProfile = { navController.navigate(ProfileMenuDestination.route) },
                navigateToGenreSelected = { navController.navigate("${MovieCatalogDestination.route}/$it") }
            )
        }
        composable(
            route = MovieCatalogDestination.routeWithArgs,
            arguments = listOf(navArgument(MovieCatalogDestination.genreIdArg) {
                type = NavType.IntType
            })
        ) {
            MovieGenreView(
                navigateUp = { navController.navigateUp() },
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToSeries = { navController.navigate(SerieMenuDestination.route) },
                navigateToSearch = { navController.navigate(SearchDestination.route) },
                navigateToProfile = { navController.navigate(ProfileMenuDestination.route) }
            )
        }

        // Series
        composable(route = SerieMenuDestination.route) {
            SerieMenuView(
                navigateUp = { navController.navigateUp() },
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToMovies = { navController.navigate(MovieMenuDestination.route) },
                navigateToSearch = { navController.navigate(SearchDestination.route) },
                navigateToProfile = { navController.navigate(ProfileMenuDestination.route) },
                navigateToGenreSelected = { navController.navigate("${SerieCatalogDestination.route}/${it}") }
            )
        }
        composable(
            route = SerieCatalogDestination.routeWithArgs,
            arguments = listOf(navArgument(SerieCatalogDestination.genreIdArg) {
                type = NavType.IntType
            })
        ) {
            SerieCatalogView(
                navigateUp = { navController.navigateUp() },
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToMovies = { navController.navigate(MovieMenuDestination.route) },
                navigateToSearch = { navController.navigate(SearchDestination.route) },
                navigateToProfile = { navController.navigate(ProfileMenuDestination.route) }
            )
        }

        // Search
        composable(route = SearchDestination.route) {
            SearchView(
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToMovie = { navController.navigate(MovieMenuDestination.route) },
                navigateToSeries = { navController.navigate(SerieMenuDestination.route) },
                navigateToProfile = { navController.navigate(ProfileMenuDestination.route) }
            )
        }


        // Profile
        composable(route = ProfileMenuDestination.route) {
            ProfileMenuView(
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToMovie = { navController.navigate(MovieMenuDestination.route) },
                navigateToSeries = { navController.navigate(SerieMenuDestination.route) },
                navigateToSearch = { navController.navigate(SearchDestination.route) },
                navigateToProfileList = { navController.navigate(ProfileListDestination.route) },
                navigateToProfileFavorites = { navController.navigate(ProfileFavoritesDestination.route) }
            )
        }

        composable(route = ProfileListDestination.route) {
            ProfileListView(
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToMovies = { navController.navigate(MovieMenuDestination.route) },
                navigateToSeries = { navController.navigate(SerieMenuDestination.route) },
                navigateToSearch = { navController.navigate(SearchDestination.route) },
                navigateToProfile = { navController.navigate(ProfileMenuDestination.route) }
            )
        }

        composable(route = ProfileFavoritesDestination.route) {
            ProfileFavoritesView(
                navigateToHome = { navController.navigate(MovieHomeDestination.route) },
                navigateToMovies = { navController.navigate(MovieMenuDestination.route) },
                navigateToSeries = { navController.navigate(SerieMenuDestination.route) },
                navigateToSearch = { navController.navigate(SearchDestination.route) },
                navigateToProfile = { navController.navigate(ProfileMenuDestination.route) }
            )
        }

    }
}