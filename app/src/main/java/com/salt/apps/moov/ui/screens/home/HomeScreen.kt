package com.salt.apps.moov.ui.screens.home


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.salt.apps.moov.ui.components.CarouselCard
import com.salt.apps.moov.ui.components.MovieCard

@Composable
fun HomeScreen(
    navController: NavController,
) {
    Column {
        CarouselCard()
        MovieCard(navController = navController)
    }
}