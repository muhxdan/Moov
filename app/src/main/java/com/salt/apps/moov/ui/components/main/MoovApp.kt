package com.salt.apps.moov.ui.components.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.salt.apps.moov.ui.navigation.MoovScreen.DETAIL
import com.salt.apps.moov.ui.navigation.MoovScreen.FAVORITE
import com.salt.apps.moov.ui.navigation.MoovScreen.HOME
import com.salt.apps.moov.ui.screens.detail.DetailScreen
import com.salt.apps.moov.ui.screens.favorite.FavoriteScreen
import com.salt.apps.moov.ui.screens.home.HomeScreen

@Composable
fun MoovApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route?.let { route ->
        when (route) {
            HOME.route -> {
                HOME
            }

            "${DETAIL.route}/{movieId}" -> {
                DETAIL
            }

            FAVORITE.route -> FAVORITE
            else -> null
        }
    } ?: HOME

    Scaffold(
        topBar = {
            MoovTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                },
                onFavoriteClicked = {
                    navController.navigate(FAVORITE.route)
                }
            )
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = HOME.route,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(
                    route = HOME.route,
                    enterTransition = {
                        return@composable slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Up, tween(500)
                        )
                    },
                    exitTransition = {
                        return@composable slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start, tween(500)
                        )
                    },
                    popEnterTransition = {
                        return@composable slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.End, tween(500)
                        )
                    },
                    content = {
                        HomeScreen(
                            navController = navController,
                        )
                    }
                )
                composable(
                    route = "${DETAIL.route}/{movieId}",
                    arguments = listOf(
                        navArgument(name = "movieId") {
                            type = NavType.IntType
                        }
                    ),
                    content = {
                        val movieId = backStackEntry?.arguments?.getInt("movieId")
                        DetailScreen(movieId = movieId)
                    }
                )

                composable(
                    route = FAVORITE.route,
                    enterTransition = {
                        return@composable slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start, tween(500)
                        )
                    },
                    exitTransition = {
                        return@composable slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.End, tween(500)
                        )
                    },
                    popEnterTransition = {
                        return@composable slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start, tween(500)
                        )
                    },
                    content = {
                        FavoriteScreen(
                            navController = navController,
                        )
                    }
                )
            }
        }
    )
}
