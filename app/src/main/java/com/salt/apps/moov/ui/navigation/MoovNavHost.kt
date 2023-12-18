package com.salt.apps.moov.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salt.apps.moov.ui.navigation.MoovScreen.DETAIL
import com.salt.apps.moov.ui.navigation.MoovScreen.FAVORITE
import com.salt.apps.moov.ui.navigation.MoovScreen.HOME
import com.salt.apps.moov.ui.screens.detail.DetailScreen
import com.salt.apps.moov.ui.screens.favorite.FavoriteScreen
import com.salt.apps.moov.ui.screens.home.HomeScreen

@Composable
fun MoovNavHost(
    navBackStackEntry: NavBackStackEntry?,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HOME.route,
        modifier = modifier
    ) {
        composable(HOME.route) {
            HomeScreen(
                navController
            )
        }
        composable(
            route = DETAIL.route + "/{movieId}",
            arguments = listOf(
                navArgument(name = "movieId") {
                    type = NavType.IntType
                }
            )
        ) {
            DetailScreen(navBackStackEntry?.arguments?.getInt("movieId"))
        }
        composable(FAVORITE.route) {
            FavoriteScreen()
        }
    }
}