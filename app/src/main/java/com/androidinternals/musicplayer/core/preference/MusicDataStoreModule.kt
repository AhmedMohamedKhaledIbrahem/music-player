package com.androidinternals.musicplayer.core.preference

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MusicDataStoreModule {
    @Binds
    @Singleton
    abstract fun bindMusicDataStore(
        musicDataStoreImpl: MusicDataStoreImpl
    ): MusicDataStore

}