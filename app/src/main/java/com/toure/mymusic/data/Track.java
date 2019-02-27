package com.toure.mymusic.data;

import java.util.Locale;

import androidx.annotation.NonNull;

public class Track {
    private String name;
    private String url;

    public Track(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s %s", name, url);
    }
}
