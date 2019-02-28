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
     * Get the album with the specified mbid
     *
     * @param mbid
     * @return Album with the specified mbid
     */
    @Query("SELECT * FROM albums where mbid = :mbid")
    LiveData<Album> getAlbum(String mbid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Album album);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Album album);

    @Delete
    void delete(Album album);


}
