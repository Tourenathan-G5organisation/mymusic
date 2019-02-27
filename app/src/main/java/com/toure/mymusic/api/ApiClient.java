package com.toure.mymusic.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toure.mymusic.data.Album;
import com.toure.mymusic.data.AlbumQuery;
import com.toure.mymusic.data.ArtistQuery;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson =
                    new GsonBuilder()
                            .registerTypeAdapter(ArtistQuery.class, new ArtistDeserializer())
                            .registerTypeAdapter(AlbumQuery.class, new AlbumDeserialiser())
                            .registerTypeAdapter(Album.class, new AlbumInfoDeserialiser())
                            .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
