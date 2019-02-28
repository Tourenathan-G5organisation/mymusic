package com.toure.mymusic;

import android.util.Log;

import com.toure.mymusic.api.ApiClient;
import com.toure.mymusic.api.ApiInterface;
import com.toure.mymusic.data.Album;
import com.toure.mymusic.util.Utility;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumDetailsViewModel extends ViewModel {

    private static final String TAG = AlbumDetailsViewModel.class.getSimpleName();
    private String albumName;
    private String artistName;
    private MutableLiveData<Album> albumLiveData = new MutableLiveData<>();

    private MutableLiveData<Boolean> error = new MutableLiveData<>();

    public AlbumDetailsViewModel(String albumName, String artistName) {
        this.albumName = albumName;
        this.artistName = artistName;
        error.postValue(false);
    }

    public LiveData<Album> getAlbumLiveData() {
        if (albumLiveData == null) {
            albumLiveData = new MutableLiveData<>();
        }
        return albumLiveData;
    }

    public LiveData<Boolean> getErrorState() {
        return error;
    }

    private void getAlbumInfo(String artistName, String albumName) {
        Call<Album> call = ApiClient.getClient().create(ApiInterface.class).getAlbumDetails(artistName, albumName, Utility.getApiKey());
        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                albumLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }
}
