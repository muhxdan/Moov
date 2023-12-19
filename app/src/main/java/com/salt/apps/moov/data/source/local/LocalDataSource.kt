package com.salt.apps.moov.data.source.local

import com.salt.apps.moov.data.source.local.entity.MoovEntity
import com.salt.apps.moov.data.source.local.room.MoovDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val moovDao: MoovDao
) {
    suspend fun insertMoovs(moovs: List<MoovEntity>) = moovDao.insertAllMoov(moovs)

//    suspend fun insertMovie(moovs: MovieEntity) = movieDao.insertMovie(moovs)

    fun getMoovs(type: String) = moovDao.getAllMoov(type)

//    fun getFavoriteMoovs() = moovDao.getFavoriteMoov()

//    fun isFavoriteMoov(movieId: Int) = moovDao.isFavoriteMovie(movieId)

//    suspend fun updateMoov(movieId: Int, isFavorite: Boolean) = moovDao.updateMoovById(movieId, isFavorite)
}