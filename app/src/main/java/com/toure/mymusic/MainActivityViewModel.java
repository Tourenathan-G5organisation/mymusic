package com.toure.mymusic;

import android.app.Application;

import com.toure.mymusic.data.Album;
import com.toure.mymusic.data.AppDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = MainActivityViewModel.class.getSimpleName();

    private LiveData<List<Album>> albumsLive;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        albumsLive = database.albumDao().getAllAlbums();
    }

    public LiveData<List<Album>> getAlbums() {
        return albumsLive;
    }
}
