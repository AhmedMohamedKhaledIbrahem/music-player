package com.androidinternals.musicplayer.feature.music.data.service.local

import android.util.Log
import com.androidinternals.musicplayer.core.database.MusicPlayerDatabase
import com.androidinternals.musicplayer.core.database.dao.MusicDao
import com.androidinternals.musicplayer.core.database.entity.MusicEntity
import com.androidinternals.musicplayer.core.preference.MusicDataStore
import com.androidinternals.musicplayer.feature.music.data.util.syncMusicData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface MusicLocal {
    fun getAllSongs(): Flow<List<MusicEntity>>
    suspend fun addSongsFromMusicProvider()
}

class MusicLocalImpl @Inject constructor(
    private val musicProvider: MusicProvider,
    private val dao: MusicDao,
    private val database: MusicPlayerDatabase,
    private val musicDataStore: MusicDataStore
) : MusicLocal {
    override fun getAllSongs(): Flow<List<MusicEntity>> {
        return try {
            dao.getAllSongs()
        }catch (e: Exception){
            Log.d(TAG, "getAllSongs: Exception:${e.message}")
            emptyFlow()
        }
    }

    private companion object {
        private val TAG = MusicLocalImpl::class.java.simpleName
    }

    override suspend fun addSongsFromMusicProvider() {
        try {
            val syncMusicData = musicDataStore.syncMusicData.first()
            if (syncMusicData) {
                return
            }
            val songs = musicProvider.getAllSongs()
            val entities = songs.map { song ->
                MusicEntity(
                    id = song.mediaStoreId,
                    title = song.title,
                    artist = song.artist,
                    album = song.album,
                    duration = song.duration,
                    contentUri = song.contentUri,
                    albumArtUri = song.albumArtUri,
                    dateAddedSeconds = song.dateAddedSeconds
                )
            }
            database.syncMusicData(entities = entities)
            musicDataStore.setSyncMusicData(true)
        } catch (e: Exception) {
            Log.e(TAG, "addSongsFromMusicProvider: Exception : ${e.message} ")
        }
    }

}