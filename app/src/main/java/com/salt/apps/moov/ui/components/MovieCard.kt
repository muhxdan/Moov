package com.salt.apps.moov.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.salt.apps.moov.data.Resource
import com.salt.apps.moov.ui.screens.home.HomeViewModel
import com.salt.apps.moov.utilities.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(
    navController: NavController,
    clickable: Boolean = true,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val popularMoviesState by homeViewModel.popularMoviesState.collectAsState()
    when (popularMoviesState) {
        is Resource.Loading -> {}

        is Resource.Success -> {
            val movies = (popularMoviesState as Resource.Success).data
            LazyColumn(
                modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                items(movies.size) { index ->
                    val movie = movies[index]
                    Column {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.background,
                            ),
                            onClick = {
//            if(clickable){
//              navController.navigate("${CinemazeScreen.DETAIL.route}/${movie.id}")
//            }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .border(
                                    1.dp,
                                    Color.LightGray.copy(alpha = .2f),
                                    MaterialTheme.shapes.medium
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                SubcomposeAsyncImage(
                                    model = Constants.getImageUrl(movie.posterPath ?: ""),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(130.dp)
                                        .height(150.dp),
                                    contentScale = ContentScale.Crop,
                                ) {
                                    val state = painter.state
                                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
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
                                    } else {
                                        SubcomposeAsyncImageContent()
                                    }
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = movie.title,
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = movie.overview.takeIf { it?.isNotBlank() == true }
                                            ?: "N/A",
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(15.dp))
                                    Text(
                                        text = "Release date: ${movie.releaseDate}",
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        is Resource.Error -> {}
    }


}
