package com.toure.mymusic;

import com.toure.mymusic.data.Artist;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    private static final String TAG = SearchViewModel.class.getSimpleName();

    private MutableLiveData<List<Artist>> artistLive = new MutableLiveData<>();

    public void setArtistData(List<Artist> artistData) {
        this.artistLive.setValue(artistData);
    }

    public LiveData<List<Artist>> getArtistLive() {
        if (artistLive == null) {
            artistLive = new MutableLiveData<>();
        }
        return artistLive;
    }
}
