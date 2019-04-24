package com.toure.mymusic.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class ArtistDataSourceFactory extends DataSource.Factory{

    private MutableLiveData<ArtistDataSource> sourceLiveData;
    private ArtistDataSource latestSource;
    private String queryString;

    public ArtistDataSourceFactory(String queryString) {
        this.queryString = queryString;
        sourceLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        latestSource = new ArtistDataSource(queryString);
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }

    public MutableLiveData<ArtistDataSource> getSourceLiveData() {
        return sourceLiveData;
    }

    public void setQueryString(String query){
        queryString = query;
    }
}
