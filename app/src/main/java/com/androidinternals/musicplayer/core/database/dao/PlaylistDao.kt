package com.androidinternals.musicplayer.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.androidinternals.musicplayer.core.database.entity.FavoriteSongEntity
import com.androidinternals.musicplayer.core.database.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao: BaseDao<PlaylistEntity> {
    @Query("SELECT * FROM playlists ORDER BY createdAt DESC")
    fun gelAllPlaylists(): Flow<List<PlaylistEntity>>
    @Query("DELETE FROM playlists WHERE id = :playlistId")
    suspend fun deletePlaylist(playlistId: Long)
    @Query(
        """
        SELECT favorite_songs.*
        FROM favorite_songs
        INNER JOIN playlist_songs
        ON favorite_songs.mediaStoreId = playlist_songs.mediaStoreId
        WHERE playlist_songs.playlistId = :playlistId
        ORDER BY playlist_songs.addedAt DESC
        """
    )
    fun getAllPlaylistSongs(playlistId: Long): Flow<List<FavoriteSongEntity>>
}