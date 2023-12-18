package com.salt.apps.moov.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.salt.apps.moov.data.source.local.entity.MoovEntity
import com.salt.apps.moov.utilities.Constants.DB_VERSION

@Database(
    entities = [MoovEntity::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class MoovDatabase : RoomDatabase() {
    abstract fun moovDao(): MoovDao
}