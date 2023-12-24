package com.salt.apps.moov.ui.components.upcoming

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.salt.apps.moov.data.model.Moov
import com.salt.apps.moov.ui.components.ImageNetworkLoader
import com.salt.apps.moov.ui.navigation.MoovScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselMovieItem(
    moov: Moov,
    sizeScale: Float,
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .graphicsLayer(
                scaleX = sizeScale,
                scaleY = sizeScale
            ),
        onClick = {
            navController.navigate("${MoovScreen.DETAIL.route}/${moov.id}")
        }
    ) {
        ImageNetworkLoader(
            imageUrl = moov.backdropPath ?: "",
            voteAverage = moov.voteAverage?.toFloat() ?: 0f,
        )
    }
}