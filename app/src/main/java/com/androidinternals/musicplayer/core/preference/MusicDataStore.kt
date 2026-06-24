package com.androidinternals.musicplayer.core.preference

import kotlinx.coroutines.flow.Flow

interface MusicDataStore {
    val syncMusicData: Flow<Boolean>
    suspend fun setSyncMusicData(value: Boolean)
}