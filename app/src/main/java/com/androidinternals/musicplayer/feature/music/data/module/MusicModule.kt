package com.androidinternals.musicplayer.feature.music.data.module

import com.androidinternals.musicplayer.feature.music.data.repoistory.MusicRepositoryImpl
import com.androidinternals.musicplayer.feature.music.data.service.local.MusicLocal
import com.androidinternals.musicplayer.feature.music.data.service.local.MusicLocalImpl
import com.androidinternals.musicplayer.feature.music.data.service.local.MusicProvider
import com.androidinternals.musicplayer.feature.music.data.service.local.MusicProviderImpl
import com.androidinternals.musicplayer.feature.music.domain.repoistory.MusicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class MusicLocalServiceModule {

    @Binds
    @Singleton
    abstract fun bindMusicProvider(
        impl: MusicProviderImpl
    ): MusicProvider

    @Binds
    @Singleton
    abstract fun bindMusicLocal(
        impl: MusicLocalImpl
    ): MusicLocal
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MusicRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMusicRepository(
        musicRepositoryImpl: MusicRepositoryImpl
    ): MusicRepository

}