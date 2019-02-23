package com.toure.mymusic.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.toure.mymusic.data.Artist;
import com.toure.mymusic.data.ArtistQuery;

import java.lang.reflect.Type;
import java.util.List;

public class ArtistDeserializer implements JsonDeserializer<ArtistQuery> {
    @Override
    public ArtistQuery deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        final JsonObject repoJsonObject = json.getAsJsonObject().getAsJsonObject("results");
        int startPage = Integer.parseInt(repoJsonObject.getAsJsonObject("opensearch:Query").get("startPage").getAsString());
        int totalResults = repoJsonObject.get("opensearch:totalResults").getAsInt();
        int itemsPerPage = repoJsonObject.get("opensearch:itemsPerPage").getAsInt();
        int start = repoJsonObject.get("opensearch:startIndex").getAsInt();
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Artist>>() {
        }.getType();
        JsonArray artistArray = repoJsonObject.getAsJsonObject("artistmatches").getAsJsonArray("artist");
        List<Artist> artists = gson.fromJson(artistArray, collectionType);

        return new ArtistQuery(startPage, totalResults, itemsPerPage, start, artists);
    }
}
