package com.toure.mymusic;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BestAlbumViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private String artistName;

    BestAlbumViewModelFactory(String artistName) {
        this.artistName = artistName;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BestAlbumViewModel(artistName);
    }
}
