package com.toure.mymusic.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class ArtistBestAlbumSourceFactory extends DataSource.Factory {

    private MutableLiveData<ArtistBestAlbumSource> sourceLiveData;
    private ArtistBestAlbumSource latestSource;
    private String artistName;

    public ArtistBestAlbumSourceFactory(String queryString) {
        this.artistName = queryString;
        sourceLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        latestSource = new ArtistBestAlbumSource(artistName);
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }

    public MutableLiveData<ArtistBestAlbumSource> getSourceLiveData() {
        return sourceLiveData;
    }
}
