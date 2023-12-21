package com.goldbach.absolutecinema.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.goldbach.absolutecinema.ui.viewmodels.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.goldbach.absolutecinema.ui.navigation.MovieNavHost
import com.goldbach.absolutecinema.ui.views.HomeView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp(navController: NavHostController = rememberNavController()) {
    MovieNavHost(navController = navController)
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
//    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = { MovieTopAppBar(scrollBehavior = scrollBehavior) }
//    ) {
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(it)
//        ) {
//            val movieViewModel: HomeViewModel =
//                viewModel(factory = HomeViewModel.Factory)
//            HomeView(movieUiState = movieViewModel.movieUiState, retryAction = { /*TODO*/ })
//        }
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Movie",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        modifier = modifier
    )
}