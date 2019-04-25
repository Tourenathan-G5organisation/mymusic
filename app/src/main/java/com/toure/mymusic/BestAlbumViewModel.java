package com.toure.mymusic;

import com.toure.mymusic.data.Album;
import com.toure.mymusic.data.AppExecutors;
import com.toure.mymusic.paging.ArtistBestAlbumSource;
import com.toure.mymusic.paging.ArtistBestAlbumSourceFactory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class BestAlbumViewModel extends ViewModel {

    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;
    public static final int NO_RESULTS = 2;
    private static final String TAG = BestAlbumViewModel.class.getSimpleName();
    private PagedList.Config pagedListConfig;
    private LiveData<Integer> searchStatus;
    private LiveData<PagedList<Album>> albumList;
    private LiveData<ArtistBestAlbumSource> mostRecentDataSource;
    private ArtistBestAlbumSourceFactory artistDataSourceFactory;

    BestAlbumViewModel(String artistName){
        pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(ArtistBestAlbumSource.PAGE_SIZE)
                .build();

        artistDataSourceFactory = new ArtistBestAlbumSourceFactory(artistName);
        mostRecentDataSource = artistDataSourceFactory.getSourceLiveData();
        albumList = (new LivePagedListBuilder<Integer,Album>(artistDataSourceFactory, pagedListConfig))
                .setFetchExecutor(AppExecutors.getInstance().networkIO())
                .build();

        searchStatus = Transformations.switchMap(mostRecentDataSource, ArtistBestAlbumSource::getSearchStatus);
    }

    LiveData<PagedList<Album>> getAlbumList() {
        return albumList;
    }

    LiveData<Integer> getSearchStatus() {
        return searchStatus;
    }
}
