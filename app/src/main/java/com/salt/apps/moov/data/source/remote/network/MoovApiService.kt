package com.salt.apps.moov.data.source.remote.network

import com.salt.apps.moov.data.source.remote.response.MoovResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoovApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MoovResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MoovResponse
}