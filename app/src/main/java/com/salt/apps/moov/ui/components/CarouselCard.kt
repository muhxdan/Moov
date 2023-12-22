package com.salt.apps.moov.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.salt.apps.moov.data.Resource
import com.salt.apps.moov.ui.screens.home.HomeViewModel
import com.salt.apps.moov.utilities.Constants.getImageUrl
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselCard(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val upcomingMoviesState by homeViewModel.upcomingMoviesState.collectAsState()


    val pageCount = Int.MAX_VALUE
    val pagerState = rememberPagerState(
        pageCount = { pageCount },
        initialPage = pageCount / 2,
    )

    when (upcomingMoviesState) {
        is Resource.Loading -> {}

        is Resource.Success -> {
            val movies = (upcomingMoviesState as Resource.Success).data
            Column(
                modifier = Modifier.fillMaxWidth()
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
                                durationMillis = 250,
                            )
                        )

                        val index = page % movies.size
                        val imageUrl = movies[index]
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .graphicsLayer(
                                    scaleX = sizeScale,
                                    scaleY = sizeScale
                                ),
                        ) {
                            SubcomposeAsyncImage(
                                model = getImageUrl(imageUrl.backdropPath ?: ""),
                                contentDescription = null,
                            ) {
                                when (painter.state) {
                                    is AsyncImagePainter.State.Loading -> {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .wrapContentSize(Alignment.Center)
                                        ) {
                                            CircularProgressIndicator(
                                                strokeWidth = 3.dp,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }

                                    is AsyncImagePainter.State.Error -> {

                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .wrapContentSize(Alignment.Center)
                                        ) {
                                            Text(
                                                "IMAGE URL NOT FOUND",
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                    }

                                    else -> {
                                        SubcomposeAsyncImageContent(
                                            contentScale = ContentScale.Crop,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            LaunchedEffect(key1 = pagerState.currentPage) {
                launch {
                    while (true) {
                        delay(3000)
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

        is Resource.Error -> {}
    }
}