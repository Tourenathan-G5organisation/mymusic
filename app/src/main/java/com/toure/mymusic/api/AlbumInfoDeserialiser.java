package com.toure.mymusic.api;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.toure.mymusic.data.Album;
import com.toure.mymusic.data.Image;
import com.toure.mymusic.data.Track;

import java.lang.reflect.Type;
import java.util.List;

public class AlbumInfoDeserialiser implements JsonDeserializer<Album> {
    @Override
    public Album deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject repoJsonObject = json.getAsJsonObject().getAsJsonObject("album");
        String name = repoJsonObject.get("name") != null ? repoJsonObject.get("name").getAsString() : "";
        String artist = repoJsonObject.get("artist") != null ? repoJsonObject.get("artist").getAsString() : "";
        String mbid = repoJsonObject.get("mbid") != null ? repoJsonObject.get("mbid").getAsString() : "";
        String url = repoJsonObject.get("url") != null ? repoJsonObject.get("url").getAsString() : "";
        int playCount = repoJsonObject.get("playcount").getAsInt();
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Image>>() {
        }.getType();
        List<Image> images = gson.fromJson(repoJsonObject.getAsJsonArray("image"), collectionType);
        collectionType = new TypeToken<List<Track>>() {
        }.getType();
        List<Track> tracks = gson.fromJson(repoJsonObject.
                getAsJsonObject("tracks").getAsJsonArray("track"), collectionType);
        return new Album(name, playCount, mbid, url, images, artist, tracks);
    }
}
