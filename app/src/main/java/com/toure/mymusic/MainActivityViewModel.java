package com.toure.mymusic;

import android.app.Application;

import com.toure.mymusic.data.Album;
import com.toure.mymusic.data.AppDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = MainActivityViewModel.class.getSimpleName();

    private LiveData<PagedList<Album>> albumsLive;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        PagedList.Config myPagingConfig = new PagedList.Config.Builder()
                .setPageSize(50)
                .setEnablePlaceholders(true)
                .build();

        albumsLive = new LivePagedListBuilder<>(database.albumDao().getAllAlbums(), myPagingConfig).build();
    }

    LiveData<PagedList<Album>> getAlbums() {
        return albumsLive;
    }
}
