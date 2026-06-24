package com.androidinternals.musicplayer.feature.music.data.service.local

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresPermission
import com.androidinternals.musicplayer.feature.music.data.model.MediaStoreSong
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MusicProvider {
    suspend fun getAllSongs(): List<MediaStoreSong>
}

class MusicProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : MusicProvider {
    private companion object {
        val TAG: String = MusicProviderImpl::class.java.simpleName
    }

    @SuppressLint("NewApi")
    @RequiresPermission(
        anyOf = [
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_MEDIA_AUDIO
        ]
    )
    override suspend fun getAllSongs(): List<MediaStoreSong> = withContext(Dispatchers.IO) {
        val songs = mutableListOf<MediaStoreSong>()
        val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.ALBUM_ID
        )
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"
        try {
            context
                .contentResolver
                .query(
                    collection,
                    projection,
                    selection,
                    null,
                    sortOrder
                )?.use { cursor ->
                    val idColumn = cursor.getColumnIndex(
                        MediaStore.Audio.Media._ID
                    )
                    val titleColumn = cursor.getColumnIndex(
                        MediaStore.Audio.Media.TITLE
                    )
                    val artistColumn = cursor.getColumnIndex(
                        MediaStore.Audio.Media.ARTIST
                    )
                    val albumColumn = cursor.getColumnIndex(
                        MediaStore.Audio.Media.ALBUM
                    )
                    val durationColumn = cursor.getColumnIndex(
                        MediaStore.Audio.Media.DURATION
                    )
                    val dateAddedColumn = cursor.getColumnIndex(
                        MediaStore.Audio.Media.DATE_ADDED
                    )
                    val albumIdColumn = cursor.getColumnIndex(
                        MediaStore.Audio.Media.ALBUM_ID
                    )
                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(idColumn)

                        val albumId = cursor.getLong(albumIdColumn)

                        val songUri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            id
                        )

                        val albumArtUri = ContentUris.withAppendedId(
                            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                            albumId
                        )
                        val title = cursor.getString(titleColumn)
                        val artist = cursor.getString(artistColumn)
                        val album = cursor.getString(albumColumn)
                        val duration = cursor.getLong(durationColumn)
                        val dateAdded = cursor.getLong(dateAddedColumn)
                        songs.add(
                            MediaStoreSong(
                                mediaStoreId = id,
                                title = title.orEmpty(),
                                artist = artist.orEmpty(),
                                album = album,
                                duration = duration,
                                contentUri = songUri.toString(),
                                albumArtUri = albumArtUri.toString(),
                                dateAddedSeconds = dateAdded
                            )
                        )
                    }
                }
            songs
        } catch (e: Exception) {
            Log.d(TAG, "getAllSongs: exception ${e.message} ")
            emptyList<MediaStoreSong>()
        }
    }

}