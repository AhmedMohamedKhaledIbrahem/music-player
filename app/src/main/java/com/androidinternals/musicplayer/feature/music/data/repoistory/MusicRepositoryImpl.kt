package com.androidinternals.musicplayer.feature.music.data.repoistory

import com.androidinternals.musicplayer.feature.music.data.mapper.toDomain
import com.androidinternals.musicplayer.feature.music.data.service.local.MusicLocal
import com.androidinternals.musicplayer.feature.music.domain.entity.Music
import com.androidinternals.musicplayer.feature.music.domain.repoistory.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localService: MusicLocal
): MusicRepository {
    override fun getAllSongs(): Flow<List<Music>> {
        return localService.getAllSongs().map { entities ->
            entities.map {
                it.toDomain()
            }
        }
    }

    override suspend fun addSongsFromMusicProvider() {
        localService.addSongsFromMusicProvider()
    }
}