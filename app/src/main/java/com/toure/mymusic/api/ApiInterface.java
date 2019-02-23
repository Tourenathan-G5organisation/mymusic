package com.toure.mymusic.api;

import com.toure.mymusic.data.ArtistQuery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("?method=artist.search&format=json")
    Call<ArtistQuery> searchArtist(@Query("artist") String artistName, @Query("api_key") String apiKey);
}
