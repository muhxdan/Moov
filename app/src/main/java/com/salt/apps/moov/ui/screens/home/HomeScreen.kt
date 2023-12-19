package com.salt.apps.moov.ui.screens.home


import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.salt.apps.moov.data.Resource
import com.salt.apps.moov.utilities.Constants.getImageUrl
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
//    val popularMoviesState by homeViewModel.popularMovies.collectAsState(null)
    val popularMoviesState by homeViewModel.popularMoviesState.collectAsState()
    // Handle UI based on the popularMoviesState
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background),
//        contentAlignment = Alignment.Center
//    ) {
    when (popularMoviesState) {
        is Resource.Loading -> {
            Log.i("HomeScreen", "Loading...")
            CircularProgressIndicator(
                strokeWidth = 5.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        is Resource.Success -> {
            val movies = (popularMoviesState as Resource.Success).data
            Log.i("HomeScreen", movies.toString())
            CarouselCard(movies.map { it.posterPath ?: "" })

        }

        is Resource.Error -> {
            val errorMessage = (popularMoviesState as Resource.Error).message
            Log.e("HomeScreen", errorMessage.toString())
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselCard(
    listImage: List<String>
) {
    val pageCount = Int.MAX_VALUE
    val pagerState = rememberPagerState(
        pageCount = { pageCount },
        initialPage = pageCount / 2
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            HorizontalPager(
                modifier = Modifier.height(160.dp),
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 60.dp),
            ) { page ->
                val sizeScale by animateFloatAsState(
                    if (pagerState.currentPage == page) 1f else .85f,
                    label = "",
                    animationSpec = tween(
                        durationMillis = 200,
                    )
                )

                val index = page % listImage.size
                val imageUrl = listImage[index]
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .graphicsLayer(
                            scaleX = sizeScale,
                            scaleY = sizeScale
                        ),
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(getImageUrl(imageUrl))
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        launch {
            while (true) {
                delay(5000)
                withContext(NonCancellable) {
                    if (pagerState.currentPage + 1 in 0..Int.MAX_VALUE) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        val initPage = Int.MAX_VALUE / 2
                        pagerState.animateScrollToPage(initPage)
                    }
                }
            }
        }
    }
}
