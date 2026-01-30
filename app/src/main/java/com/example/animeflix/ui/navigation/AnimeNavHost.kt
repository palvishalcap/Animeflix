package com.example.animeflix.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.animeflix.ui.composables.AnimeDetailScreen
import com.example.animeflix.ui.composables.AnimeListScreen
import com.example.animeflix.ui.theme.AnimeViewModel

@Composable
fun AnimeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AnimeViewModel
) {

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = AnimeList) {

        composable<AnimeList> {
            AnimeListScreen(
                animeViewModel = viewModel,
                onAnimeItemClicked = {
                    navController.navigate(AnimeDetail(it))
                }
            )
        }

        // TODO :: find out diff bw composable<> and composable()
        composable<AnimeDetail> { backStackEntry ->
            val animeDetail: AnimeDetail  = backStackEntry.toRoute()
            AnimeDetailScreen(
                animeId = animeDetail.animeId,
                viewModel = viewModel
            )

        }
    }
}