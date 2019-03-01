package com.toure.mymusic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AlbumDetailsViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private String albumName;
    private String artistName;
    private boolean fromDB;
    private Application application;


    public AlbumDetailsViewModelFactory(@NonNull Application application) {
        super(application);
    }

    public AlbumDetailsViewModelFactory(@NonNull Application application, String albumName, String artistName, boolean fromDB) {
        super(application);
        this.albumName = albumName;
        this.artistName = artistName;
        this.fromDB = fromDB;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AlbumDetailsViewModel(application, albumName, artistName, fromDB);
    }
}
