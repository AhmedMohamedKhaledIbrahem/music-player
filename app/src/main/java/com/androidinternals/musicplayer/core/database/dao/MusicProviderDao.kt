package com.androidinternals.musicplayer.core.database.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query

@Dao
interface MusicProviderDao {
    @Query("SELECT * FROM favorite_songs ORDER BY addedAt DESC")
    fun getFavoritesCursor(): Cursor

    @Query("SELECT * FROM favorite_songs WHERE mediaStoreId = :musicId")
    fun getFavoriteCursor(musicId: Long): Cursor

    @Query("SELECT * FROM playlists ORDER BY createdAt DESC")
    fun getPlaylistsCursor(): Cursor

    @Query(
        """
        SELECT favorite_songs.*
        FROM favorite_songs
        INNER JOIN playlist_songs
        ON favorite_songs.mediaStoreId = playlist_songs.mediaStoreId
        WHERE playlist_songs.playlistId = :playlistId
        """
    )
    fun getPlaylistSongsCursor(playlistId: Long): Cursor
}