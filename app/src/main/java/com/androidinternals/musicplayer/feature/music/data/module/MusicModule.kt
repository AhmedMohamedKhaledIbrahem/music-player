package com.androidinternals.musicplayer.feature.music.data.module

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.C.USAGE_MEDIA
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.androidinternals.musicplayer.feature.music.data.repoistory.MusicRepositoryImpl
import com.androidinternals.musicplayer.feature.music.data.service.local.MusicLocal
import com.androidinternals.musicplayer.feature.music.data.service.local.MusicLocalImpl
import com.androidinternals.musicplayer.feature.music.data.service.local.MusicProvider
import com.androidinternals.musicplayer.feature.music.data.service.local.MusicProviderImpl
import com.androidinternals.musicplayer.feature.music.data.service.local.player.AudioPlayerControllerImpl
import com.androidinternals.musicplayer.feature.music.data.service.local.player.PlayBackService
import com.androidinternals.musicplayer.feature.music.data.service.local.player.PlayBackServiceImpl
import com.androidinternals.musicplayer.feature.music.data.service.local.player.PlayerListener
import com.androidinternals.musicplayer.feature.music.data.service.local.player.ProgressTracker
import com.androidinternals.musicplayer.feature.music.data.service.local.player.ProgressTrackerImpl
import com.androidinternals.musicplayer.feature.music.domain.controller.AudioController
import com.androidinternals.musicplayer.feature.music.domain.repoistory.MusicRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Binds
    @Singleton
    abstract fun bindProgressTracker(
        impl: ProgressTrackerImpl
    ): ProgressTracker

    @Binds
    @Singleton
    abstract fun bindPlayerListener(
        impl: PlayerListener
    ): Player.Listener

    @Binds
    @Singleton
    abstract fun bindPlayBackService(
        impl: PlayBackServiceImpl
    ): PlayBackService

    @Binds
    @Singleton
    abstract fun bindAudioController(
        impl: AudioPlayerControllerImpl
    ): AudioController
    companion object {
        @Provides
        @Singleton
        fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer {
            val audioAttribute = AudioAttributes.Builder()
                .setContentType(AUDIO_CONTENT_TYPE_MUSIC)
                .setUsage(USAGE_MEDIA)
                .build()
            return ExoPlayer.Builder(context)
                .setAudioAttributes(audioAttribute,true)
                .setHandleAudioBecomingNoisy(true)
                .build()
        }
    }
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