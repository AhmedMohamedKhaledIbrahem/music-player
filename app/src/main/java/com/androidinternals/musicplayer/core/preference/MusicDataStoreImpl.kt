package com.androidinternals.musicplayer.core.preference

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.musicDataStore by preferencesDataStore(name = "music_data_store")
class MusicDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): MusicDataStore {

    override val syncMusicData: Flow<Boolean>  = context.musicDataStore.data.map {
        it[PreferencesKeys.SYNC_MUSIC_DATA] ?: false
    }
    override suspend fun setSyncMusicData(value: Boolean) {
        context.musicDataStore.edit { preferences ->
            preferences[PreferencesKeys.SYNC_MUSIC_DATA] = value
        }
    }

    private object PreferencesKeys{
        val SYNC_MUSIC_DATA = booleanPreferencesKey("sync_music_data")
    }

}