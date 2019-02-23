package com.toure.mymusic.data;

import java.util.List;
import java.util.Locale;

public class Artist {

    private String name;
    private Integer listeners;
    private String mbid;
    private String url;
    private Integer streamable;
    private List<Image> image;


    public Artist(String name, Integer listeners, String mbid, String url, int streamable, List<Image> image) {
        this.name = name;
        this.listeners = listeners;
        this.mbid = mbid;
        this.url = url;
        this.streamable = streamable;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getListeners() {
        return listeners;
    }

    public void setListeners(Integer listeners) {
        this.listeners = listeners;
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

    public int getStreamable() {
        return streamable;
    }

    public void setStreamable(int streamable) {
        this.streamable = streamable;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    /**
     * Get the best image quality among the available artist images
     *
     * @return image url
     */
    public String getImageUrl() {
        String url = null;
        if (image.size() > 0) {
            for (int i = image.size() - 1; i >= 0; i -= 1) {
                url = image.get(i).getText();
                break;
            }
            return url;
        }
        return url;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Artist name: %s\nListeners: %d\nmbid: %s\nurl: %s\nStreamable:%d\nimage:%s",
                name, listeners, mbid, url, streamable, image.get(0).toString());
    }
}
