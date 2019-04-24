package com.toure.mymusic;

import com.toure.mymusic.data.AppExecutors;
import com.toure.mymusic.data.Artist;
import com.toure.mymusic.paging.ArtistDataSource;
import com.toure.mymusic.paging.ArtistDataSourceFactory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class SearchViewModel extends ViewModel {

    private static final String TAG = SearchViewModel.class.getSimpleName();
    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;
    public static final int NO_RESULTS = 2;
    private PagedList.Config pagedListConfig;
    private LiveData<Integer> searchStatus = new MutableLiveData<>();
    private LiveData<PagedList<Artist>> artistLive;
    private LiveData<ArtistDataSource> mostRecentDataSource;
    private ArtistDataSourceFactory artistDataSourceFactory;


    public SearchViewModel() {
        artistDataSourceFactory = new ArtistDataSourceFactory(null);
        mostRecentDataSource = artistDataSourceFactory.getSourceLiveData();

        pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ArtistDataSource.PAGE_SIZE)
                        .build();

        artistLive = (new LivePagedListBuilder<Integer, Artist>(artistDataSourceFactory, pagedListConfig))
                .setFetchExecutor(AppExecutors.getInstance().networkIO())
                .build();
    }

    void setQueryString(String queryString) {
        artistDataSourceFactory.setQueryString(queryString);

        if (mostRecentDataSource != null) {
            mostRecentDataSource.getValue().invalidate();
            searchStatus = Transformations.switchMap(mostRecentDataSource, ArtistDataSource::getSearchStatus);
        }



    }

    LiveData<PagedList<Artist>> getArtistLiveData() {
        if (artistLive == null) {
            artistLive = new MutableLiveData<>();
        }
        return artistLive;
    }

    LiveData<Integer> getSearchStatus() {
        if (searchStatus == null) {
            searchStatus = new MutableLiveData<>();
        }
        return searchStatus;
    }
}
