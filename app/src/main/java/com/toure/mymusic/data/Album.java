package com.toure.mymusic.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Album {

    private Artist artist;
    private String name;
    @SerializedName("playcount")
    private Integer playCount;
    private String mbid;
    private String url;
    private List<Image> image;

    public Album(String name, Integer playCount, String mbid, String url, List<Image> image, Artist artist) {
        this.name = name;
        this.playCount = playCount;
        this.mbid = mbid;
        this.url = url;
        this.image = image;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * Get the best image quality among the available artist images
     *
     * @return image url
     */
    public String getImageUrl() {
        if (image.size() > 0) {
            return image.get(image.size() - 1).getText();
        }
        return null;
    }
}
