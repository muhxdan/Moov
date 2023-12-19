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
    suspend fun insertAllMoov(moovs: List<MoovEntity>)

    @Query("SELECT * FROM moov_entity WHERE moovType = :moovType")
    fun getAllMoov(moovType: String): Flow<List<MoovEntity>>

//    @Query("SELECT * FROM moov_entity WHERE id=:moovId ORDER BY id")
//    fun getMoovById(moovId: Int): Flow<MoovEntity>
//
//    @Query("UPDATE moov_entity SET isFavorite = :isFavorite WHERE id = :moovId")
//    suspend fun updateMoovById(moovId: Int, isFavorite: Boolean)
//
//    @Query("SELECT * FROM moov_entity WHERE isFavorite = 1")
//    fun getFavoriteMoov(): Flow<List<MoovEntity>>
}