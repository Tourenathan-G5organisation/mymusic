package com.toure.mymusic;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AlbumDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private String albumName;
    private String artistName;

    public AlbumDetailsViewModelFactory(String albumName, String artistName) {
        this.albumName = albumName;
        this.artistName = artistName;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AlbumDetailsViewModel(albumName, artistName);
    }
}
