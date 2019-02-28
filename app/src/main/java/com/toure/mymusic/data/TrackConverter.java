package com.toure.mymusic.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class TrackConverter {

    @TypeConverter
    public static List<Track> toList(String value) {
        Type listType = new TypeToken<List<Track>>() {
        }.getType();
        return value == null ? null : (List<Track>) new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String toString(List<Track> tracks) {
        Gson gson = new Gson();
        return tracks == null ? null : gson.toJson(tracks);
    }

}
