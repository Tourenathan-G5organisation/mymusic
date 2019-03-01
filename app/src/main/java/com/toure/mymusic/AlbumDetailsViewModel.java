package com.toure.mymusic;

import android.app.Application;
import android.util.Log;

import com.toure.mymusic.api.ApiClient;
import com.toure.mymusic.api.ApiInterface;
import com.toure.mymusic.data.Album;
import com.toure.mymusic.data.AppDatabase;
import com.toure.mymusic.util.Utility;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumDetailsViewModel extends AndroidViewModel {

    private static final String TAG = AlbumDetailsViewModel.class.getSimpleName();
    private String albumName;
    private String artistName;
    private LiveData<Album> albumLiveData;
    private MutableLiveData<Album> albumOnlineLiveData = new MutableLiveData<>();

    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private boolean fromDB;

    public AlbumDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public AlbumDetailsViewModel(@NonNull Application application, String albumName, String artistName, boolean fromDB) {
        super(application);
        this.albumName = albumName;
        this.artistName = artistName;
        error.postValue(false);
        this.fromDB = fromDB;
        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        if (fromDB) {
            // load data from local db
            albumLiveData = database.albumDao().getAlbum(albumName, artistName);
        } else {
            // Load data from online
            getAlbumInfo(artistName, albumName);
        }

    }

    public LiveData<Album> getAlbumLiveData() {
        if (albumLiveData == null) {
            albumLiveData = albumOnlineLiveData;
        }
        return albumLiveData;
    }

    public LiveData<Boolean> getErrorState() {
        return error;
    }

    public boolean isDataFromDb() {
        return fromDB;
    }

    private void getAlbumInfo(String artistName, String albumName) {
        Call<Album> call = ApiClient.getClient().create(ApiInterface.class).getAlbumDetails(artistName, albumName, Utility.getApiKey());
        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                albumOnlineLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                error.postValue(true);
            }
        });
    }
}
