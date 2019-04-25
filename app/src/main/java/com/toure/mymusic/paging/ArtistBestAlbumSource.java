package com.toure.mymusic.paging;

import android.util.Log;

import com.toure.mymusic.SearchViewModel;
import com.toure.mymusic.api.ApiClient;
import com.toure.mymusic.api.ApiInterface;
import com.toure.mymusic.data.Album;
import com.toure.mymusic.data.AlbumQuery;
import com.toure.mymusic.util.Utility;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.toure.mymusic.BestAlbumViewModel.FAILURE;
import static com.toure.mymusic.BestAlbumViewModel.NO_RESULTS;
import static com.toure.mymusic.BestAlbumViewModel.SUCCESS;

public class ArtistBestAlbumSource extends PageKeyedDataSource<Integer, Album> {

    public static final int PAGE_SIZE = 50;
    private static final String TAG = ArtistBestAlbumSource.class.getSimpleName();
    private final int FIRST_PAGE = 1;
    private String artistName;
    private MutableLiveData<Integer> searchStatus;

    ArtistBestAlbumSource(String artistName) {
        this.artistName = artistName;
        searchStatus = new MutableLiveData<>();
        searchStatus.postValue(-1);
    }

    public MutableLiveData<Integer> getSearchStatus() {
        return searchStatus;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Album> callback) {

        Call<AlbumQuery> call = ApiClient.getClient().create(ApiInterface.class).getArtistBestAlbum(artistName, Utility.getApiKey(), FIRST_PAGE);
        call.enqueue(new Callback<AlbumQuery>() {
            @Override
            public void onResponse(Call<AlbumQuery> call, Response<AlbumQuery> response) {
                if (response.body() != null) {
                    if (response.body().getAlbums().size() > 0) {
                        callback.onResult(response.body().getAlbums(), null, FIRST_PAGE + 1);
                        searchStatus.postValue(SUCCESS);
                    } else {
                        searchStatus.postValue(NO_RESULTS);
                    }

                } else {
                    searchStatus.postValue(FAILURE);
                }
            }

            @Override
            public void onFailure(Call<AlbumQuery> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                searchStatus.postValue(FAILURE);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Album> callback) {

        Call<AlbumQuery> call = ApiClient.getClient().create(ApiInterface.class).getArtistBestAlbum(artistName, Utility.getApiKey(), params.key);
        call.enqueue(new Callback<AlbumQuery>() {
            @Override
            public void onResponse(Call<AlbumQuery> call, Response<AlbumQuery> response) {
                if (response.body() != null) {

                    searchStatus.postValue(SearchViewModel.SUCCESS);
                    if (response.body().getAlbums().size() > 0) {
                        Integer key = params.key > 1 ? params.key - 1 : null;
                        callback.onResult(response.body().getAlbums(), key);
                    }
                }
            }

            @Override
            public void onFailure(Call<AlbumQuery> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                searchStatus.postValue(FAILURE);
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Album> callback) {

        Call<AlbumQuery> call = ApiClient.getClient().create(ApiInterface.class).getArtistBestAlbum(artistName, Utility.getApiKey(), params.key);
        call.enqueue(new Callback<AlbumQuery>() {
            @Override
            public void onResponse(Call<AlbumQuery> call, Response<AlbumQuery> response) {
                if (response.body() != null) {

                    searchStatus.postValue(SearchViewModel.SUCCESS);
                    if (response.body().getAlbums().size() > 0) {
                        Integer key = (response.body().getPage() < response.body().getTotalPages()) ?
                                params.key + 1 : null;
                        callback.onResult(response.body().getAlbums(), key);
                    }
                }
            }

            @Override
            public void onFailure(Call<AlbumQuery> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                searchStatus.postValue(FAILURE);
            }
        });
    }
}
