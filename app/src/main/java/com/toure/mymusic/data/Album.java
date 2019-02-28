package com.toure.mymusic.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "albums", primaryKeys = {"album_name", "artist_name"})
public class Album {

    @ColumnInfo(name = "mbid")
    private String mbid;

    @NonNull
    @ColumnInfo(name = "album_name")
    // Used by room to reference the corresponding fields in the SQLite table
    private String name;

    @NonNull
    @ColumnInfo(name = "artist_name")
    private String artistName;

    @ColumnInfo(name = "play_count")
    @SerializedName("playcount")
    private Integer playCount;

    @ColumnInfo(name = "url")
    private String url;

    @SerializedName("image")
    @Ignore //Use by room to ignore this field when creating an object of the entity
    private List<Image> images;

    @ColumnInfo(name = "tracks")
    private List<Track> tracks;

    @ColumnInfo(name = "album_image")
    private String albumImage;

    @Ignore
    public Album(String name, Integer playCount, String mbid, String url, List<Image> images) {
        this.name = name;
        this.playCount = playCount;
        this.mbid = mbid;
        this.url = url;
        this.images = images;
    }

    @Ignore
    public Album(String name, Integer playCount, String mbid, String url, List<Image> images, String artistName) {
        this.name = name;
        this.playCount = playCount;
        this.mbid = mbid;
        this.url = url;
        this.images = images;
        this.artistName = artistName;
        getImageUrl();
    }

    @Ignore
    public Album(String name, Integer playCount, String mbid, String url, List<Image> images, String artistName, List<Track> tracks) {
        this.name = name;
        this.playCount = playCount;
        this.mbid = mbid;
        this.url = url;
        this.images = images;
        this.artistName = artistName;
        this.tracks = tracks;
        getImageUrl();
    }

    public Album(String name, Integer playCount, String mbid, String url, String albumImage, String artistName, List<Track> tracks) {
        this.name = name;
        this.playCount = playCount;
        this.mbid = mbid;
        this.url = url;
        this.albumImage = albumImage;
        this.artistName = artistName;
        this.tracks = tracks;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    @NonNull
    public String getMbid() {
        return mbid;
    }

    public void setMbid(@NonNull String mbid) {
        this.mbid = mbid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(@NonNull String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    /**
     * Get the best images quality among the available artist images
     *
     * @return images url
     */
    public String getImageUrl() {
        if (albumImage != null) {
            return albumImage;
        } else {
            if (images.size() > 0) {
                albumImage = images.get(images.size() - 1).getText();
                return albumImage;
            }
        }

        return null;
    }
}
