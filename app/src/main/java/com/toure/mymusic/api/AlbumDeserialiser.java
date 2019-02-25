package com.toure.mymusic.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.toure.mymusic.data.Album;
import com.toure.mymusic.data.AlbumQuery;

import java.lang.reflect.Type;
import java.util.List;

public class AlbumDeserialiser implements JsonDeserializer<AlbumQuery> {
    @Override
    public AlbumQuery deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final JsonObject repoJsonObject = json.getAsJsonObject().getAsJsonObject("topalbums");
        JsonArray albumArray = repoJsonObject.getAsJsonArray("album");
        JsonObject attr = repoJsonObject.getAsJsonObject("@attr");
        String artistName = attr.get("artist").getAsString();
        int page = attr.get("page").getAsInt();
        int perPage = attr.get("perPage").getAsInt();
        int totalPages = attr.get("totalPages").getAsInt();
        int total = attr.get("total").getAsInt();
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Album>>() {
        }.getType();
        List<Album> albums = gson.fromJson(albumArray, collectionType);

        return new AlbumQuery(artistName, page, perPage, totalPages, total, albums);
    }
}
