package com.toure.mymusic.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AlbumDao {

    /**
     * Get all albums available in the DB
     *
     * @return All saved albums
     */
    @Query("SELECT * FROM albums ORDER BY album_name ASC")
    LiveData<List<Album>> getAllAlbums();

    /**
     * Get the album with the specified albumName and artistName
     * @param albumName Album name
     * @param artistName Artist name
     * @return return an album with those parameters
     */
    @Query("SELECT * FROM albums where album_name = :albumName AND artist_name = :artistName")
    LiveData<Album> getAlbum(String albumName, String artistName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Album album);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Album album);

    @Delete
    void delete(Album album);


}
