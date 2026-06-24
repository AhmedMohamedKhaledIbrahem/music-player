package com.androidinternals.musicplayer.feature.music.domain.usecase

import com.androidinternals.musicplayer.feature.music.domain.entity.Music
import com.androidinternals.musicplayer.feature.music.domain.repoistory.MusicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSongsUseCase @Inject constructor(
    private val repository: MusicRepository
) {
    operator fun invoke(): Flow<List<Music>> {
        return repository.getAllSongs()
    }
}