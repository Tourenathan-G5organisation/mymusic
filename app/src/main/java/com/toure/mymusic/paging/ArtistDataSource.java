package com.toure.mymusic.paging;

import android.util.Log;

import com.toure.mymusic.SearchViewModel;
import com.toure.mymusic.api.ApiClient;
import com.toure.mymusic.api.ApiInterface;
import com.toure.mymusic.data.Artist;
import com.toure.mymusic.data.ArtistQuery;
import com.toure.mymusic.util.Utility;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.toure.mymusic.SearchViewModel.FAILURE;
import static com.toure.mymusic.SearchViewModel.NO_RESULTS;
import static com.toure.mymusic.SearchViewModel.SUCCESS;

public class ArtistDataSource extends PageKeyedDataSource<Integer, Artist> {

    public static final int PAGE_SIZE = 50;
    private static final String TAG = ArtistDataSource.class.getSimpleName();
    private final int FIRST_PAGE = 1;
    private String queryString;
    private MutableLiveData<Integer> searchStatus;


    public ArtistDataSource(String queryString) {
        this.queryString = queryString;
        searchStatus = new MutableLiveData<>();
        searchStatus.postValue(-1);
    }

    public MutableLiveData<Integer> getSearchStatus() {
        return searchStatus;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Artist> callback) {
        Log.d(TAG, "loaded page: " + FIRST_PAGE);

        Call<ArtistQuery> call = ApiClient.getClient().create(ApiInterface.class)
                .searchArtist(queryString, Utility.getApiKey(), FIRST_PAGE);

        call.enqueue(new Callback<ArtistQuery>() {
            @Override
            public void onResponse(Call<ArtistQuery> call, Response<ArtistQuery> response) {

                if (response.body() != null) {

                    List<Artist> artists = response.body().getArtists();
                    if (artists.size() > 0) {
                        searchStatus.postValue(SUCCESS);
                        callback.onResult(response.body().getArtists(), null, FIRST_PAGE + 1);
                    } else {
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

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Artist> callback) {

        Call<ArtistQuery> call = ApiClient.getClient().create(ApiInterface.class)
                .searchArtist(queryString, Utility.getApiKey(), params.key);
        call.enqueue(new Callback<ArtistQuery>() {
            @Override
            public void onResponse(Call<ArtistQuery> call, Response<ArtistQuery> response) {
                Log.d(TAG, "loaded page: " + params.key);
                if (response.body() != null) {
                    ArtistQuery body = response.body();

                    searchStatus.postValue(SearchViewModel.SUCCESS);
                    if (body.getArtists().size() > 0) {
                        Integer key = params.key > 1 ? params.key - 1 : null;
                        callback.onResult(body.getArtists(), key);
                    }


                }

            }

            @Override
            public void onFailure(Call<ArtistQuery> call, @NonNull Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                searchStatus.postValue(FAILURE);
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Artist> callback) {

        Call<ArtistQuery> call = ApiClient.getClient().create(ApiInterface.class)
                .searchArtist(queryString, Utility.getApiKey(), params.key);

        call.enqueue(new Callback<ArtistQuery>() {
            @Override
            public void onResponse(Call<ArtistQuery> call, Response<ArtistQuery> response) {
                Log.d(TAG, "loaded page: " + params.key);
                if (response.body() != null) {
                    ArtistQuery body = response.body();

                    searchStatus.postValue(SearchViewModel.SUCCESS);
                    // For loadAfter
                    if (body.getArtists().size() > 0) {
                        Integer key = (body.getStartIndex() + body.getItemsPerPage() < body.getTotalResults() - 1) ?
                                params.key + 1 : null;
                        callback.onResult(body.getArtists(), key);
                    }


                }

            }

            @Override
            public void onFailure(Call<ArtistQuery> call, @NonNull Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                searchStatus.postValue(FAILURE);
            }
        });
    }

    /**
     * Searches for the artists with names similar to search query
     *
     * @param artistName The artist name to search for.
     * @param isBefore   Determine is this method is called from loadBefore or loadAfter
     */
   /* void searchArtist(String artistName, boolean isBefore, @NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Artist> callback) {

        Call<ArtistQuery> call = ApiClient.getClient().create(ApiInterface.class)
                .searchArtist(artistName, Utility.getApiKey(), params.key);
        call.enqueue(new Callback<ArtistQuery>() {
            @Override
            public void onResponse(Call<ArtistQuery> call, Response<ArtistQuery> response) {
                Log.d(TAG, "loaded page: " + params.key);
                if (response.body() != null) {
                    ArtistQuery body = response.body();

                    searchStatus.postValue(SearchViewModel.SUCCESS);

                    if (isBefore) { // For loadBefore
                        Integer key = params.key > 1 ? params.key - 1 : null;
                        callback.onResult(body.getArtists(), key);
                    } else { // For loadAfter
                        Integer key = (body.getStartIndex() + body.getItemsPerPage() < body.getTotalResults() - 1) ?
                                params.key + 1 : null;
                        callback.onResult(body.getArtists(), key);
                    }


                }

            }

            @Override
            public void onFailure(Call<ArtistQuery> call, @NonNull Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                searchStatus.postValue(FAILURE);
            }
        });
    }*/
}
