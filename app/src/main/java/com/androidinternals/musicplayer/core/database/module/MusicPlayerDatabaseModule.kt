package com.androidinternals.musicplayer.core.database.module

import android.content.Context
import androidx.room.Room
import com.androidinternals.musicplayer.core.database.MusicPlayerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicPlayerDatabaseModule {
    @Provides
    @Singleton
    fun providerDatabase(
        @ApplicationContext context: Context
    ): MusicPlayerDatabase {
        return Room.databaseBuilder(
            context,
            MusicPlayerDatabase::class.java,
            "music_player_database"
        ).fallbackToDestructiveMigration(true).build()

    }

    @Provides
    @Singleton
    fun provideFavoriteSongDao(
        database: MusicPlayerDatabase
    ) = database.favoriteSongDao()

    @Provides
    @Singleton
    fun providePlaylistDao(
        database: MusicPlayerDatabase
    ) = database.playlistDao()

    @Provides
    @Singleton
    fun provideMusicProviderDao(
        database: MusicPlayerDatabase
    ) = database.musicProviderDao()

    @Provides
    @Singleton
    fun provideRecentlyPlayedDao(
        database: MusicPlayerDatabase
    ) = database.recentlyPlayedDao()

    @Provides
    @Singleton
    fun provideMusicDao(
        database: MusicPlayerDatabase
    ) = database.musicDao()
}