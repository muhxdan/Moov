package com.salt.apps.moov.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.salt.apps.moov.data.source.local.entity.MoovEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoovDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MoovEntity>)

    @Query("SELECT * FROM moov_entity ")
    fun getAllMovies(): Flow<List<MoovEntity>>

    @Query("SELECT * FROM moov_entity WHERE id=:imageId ORDER BY id")
    fun getMovieById(imageId: Int): Flow<MoovEntity>

    @Query("UPDATE moov_entity SET isFavorite = :isFavorite WHERE id = :movieId")
    suspend fun updateMovie(movieId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM moov_entity WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MoovEntity>>
}