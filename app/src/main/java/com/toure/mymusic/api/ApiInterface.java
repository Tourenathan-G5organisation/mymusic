package com.toure.mymusic.api;

import com.toure.mymusic.data.Album;
import com.toure.mymusic.data.AlbumQuery;
import com.toure.mymusic.data.ArtistQuery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("?method=artist.search&format=json&limit=50")
    Call<ArtistQuery> searchArtist(@Query("artist") String artistName, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("?method=artist.gettopalbums&format=json&limit=100")
    Call<AlbumQuery> getArtistBestAlbum(@Query("artist") String artistName, @Query("api_key") String apiKey);

    @GET("?method=album.getinfo&format=json")
    Call<Album> getAlbumDetails(@Query("artist") String artistName, @Query("album") String album, @Query("api_key") String apiKey);
}
