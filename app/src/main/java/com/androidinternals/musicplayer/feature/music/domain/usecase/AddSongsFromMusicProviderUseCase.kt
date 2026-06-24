package com.androidinternals.musicplayer.feature.music.domain.usecase

import com.androidinternals.musicplayer.feature.music.domain.repoistory.MusicRepository
import javax.inject.Inject

class AddSongsFromMusicProviderUseCase @Inject constructor(
    private val repository: MusicRepository
) {
    suspend operator fun invoke(){
        repository.addSongsFromMusicProvider()
    }
}