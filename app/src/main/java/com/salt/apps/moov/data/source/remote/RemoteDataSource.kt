package com.salt.apps.moov.data.source.remote

import com.salt.apps.moov.data.source.remote.network.MoovApiResponse
import com.salt.apps.moov.data.source.remote.network.MoovApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val moovApiService: MoovApiService
) {
    suspend fun getPopularMovies() = flow {
        try {
            val response = moovApiService.getPopularMovies()
            val data = response.results
            if (data.isNotEmpty()) emit(MoovApiResponse.Success(data))
            else emit(MoovApiResponse.Empty)
        } catch (e: Exception) {
            emit(MoovApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getUpComingMovies() = flow {
        try {
            val response = moovApiService.getUpcomingMovies()
            val data = response.results
            if (data.isNotEmpty()) emit(MoovApiResponse.Success(data))
            else (emit(MoovApiResponse.Empty))
        } catch (e: Exception) {
            emit(MoovApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}