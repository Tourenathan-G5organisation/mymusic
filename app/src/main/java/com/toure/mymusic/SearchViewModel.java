package com.toure.mymusic;

import android.util.Log;

import com.toure.mymusic.api.ApiClient;
import com.toure.mymusic.api.ApiInterface;
import com.toure.mymusic.data.Artist;
import com.toure.mymusic.data.ArtistQuery;
import com.toure.mymusic.util.Utility;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private static final String TAG = SearchViewModel.class.getSimpleName();
    final int SUCCESS = 1;
    final int FAILURE = 0;
    final int NO_RESULTS = 2;
    private MutableLiveData<Integer> searchStatus = new MutableLiveData<>();

    private MutableLiveData<List<Artist>> artistLive = new MutableLiveData<>();
    public SearchViewModel() {
        super();
        this.searchStatus.setValue(-1);
    }

    LiveData<List<Artist>> getArtistLive() {
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

    /**
     * Searches for the artists with names similar to search query
     * @param artistName The artist name to search for.
     */
    void searchArtist(String artistName) {

        Call<ArtistQuery> call =  ApiClient.getClient().create(ApiInterface.class)
                .searchArtist(artistName, Utility.getApiKey());
        call.enqueue(new Callback<ArtistQuery>() {
            @Override
            public void onResponse(Call<ArtistQuery> call, Response<ArtistQuery> response) {
                // Log.d(TAG, "Response: " + response.body().getArtists().get(0).toString());
                if (response.body() != null) {
                   List<Artist> artists = response.body().getArtists();
                    artistLive.postValue(artists);
                    if (artists.size()>0){
                        searchStatus.postValue(SUCCESS);
                    }else {
                        searchStatus.postValue(NO_RESULTS);
                    }

                } else {
                    searchStatus.postValue(FAILURE);
                }

            }

            @Override
            public void onFailure(Call<ArtistQuery> call, @NonNull Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                searchStatus.postValue(FAILURE);
            }
        });
    }
}
