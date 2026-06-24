package com.androidinternals.musicplayer.feature.music.data.util

import androidx.room.withTransaction
import com.androidinternals.musicplayer.core.database.MusicPlayerDatabase
import com.androidinternals.musicplayer.core.database.entity.MusicEntity

suspend fun MusicPlayerDatabase.syncMusicData(entities: List<MusicEntity>) {
    withTransaction {
        musicDao().insertAll(entities)
        if (entities.isEmpty()){
            musicDao().deleteAllSongs()
        } else {
            musicDao().deleteSongsNotIn(entities.map { it.id })
        }
    }
}