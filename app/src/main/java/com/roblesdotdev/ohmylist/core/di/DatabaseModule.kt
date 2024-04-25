package com.roblesdotdev.ohmylist.core.di

import android.content.Context
import androidx.room.Room
import com.roblesdotdev.ohmylist.core.data.local.LocalDao
import com.roblesdotdev.ohmylist.core.data.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): LocalDatabase {
        return Room
            .databaseBuilder(
                context,
                LocalDatabase::class.java,
                "localdb.db",
            ).build()
    }

    @Singleton
    @Provides
    fun providesLocalDao(localDatabase: LocalDatabase): LocalDao {
        return localDatabase.dao
    }
}
