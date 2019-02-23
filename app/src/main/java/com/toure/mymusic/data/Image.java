package com.toure.mymusic.data;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class Image {
    @SerializedName("#text")
    private String text;
    private String size;

    public Image(String text, String size) {
        this.text = text;
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("text: %s\nSize: %s", text, size);
    }
}
