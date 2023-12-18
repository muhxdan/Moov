package com.salt.apps.moov.di

import android.content.Context
import androidx.room.Room
import com.salt.apps.moov.data.source.local.room.MoovDao
import com.salt.apps.moov.data.source.local.room.MoovDatabase
import com.salt.apps.moov.utilities.Constants.DB_NAME
import com.salt.apps.moov.utilities.Constants.ENCRYPTED_DB_PASSPHRASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private val passphrase = SQLiteDatabase.getBytes(ENCRYPTED_DB_PASSPHRASE.toCharArray())
    private val factory = SupportFactory(passphrase)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MoovDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MoovDatabase::class.java,
            name = DB_NAME
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideMoovDao(database: MoovDatabase): MoovDao = database.moovDao()
}