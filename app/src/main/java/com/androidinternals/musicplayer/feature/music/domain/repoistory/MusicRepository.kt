package com.androidinternals.musicplayer.feature.music.domain.repoistory

import com.androidinternals.musicplayer.feature.music.domain.entity.Music
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    fun getAllSongs(): Flow<List<Music>>
    suspend fun addSongsFromMusicProvider()
}