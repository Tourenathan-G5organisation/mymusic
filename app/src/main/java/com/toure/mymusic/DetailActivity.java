package com.toure.mymusic;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.toure.mymusic.adapter.TrackAdapter;
import com.toure.mymusic.api.ApiClient;
import com.toure.mymusic.api.ApiInterface;
import com.toure.mymusic.data.Album;
import com.toure.mymusic.data.AppDatabase;
import com.toure.mymusic.data.AppExecutors;
import com.toure.mymusic.util.Utility;

import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String ARTIST_NAME = "artist_name";
    public static final String ALBUM_NAME = "album_name";
    public static final String ALBUM_FROM_DB = "album_from_db";

    static final String TAG = DetailActivity.class.getSimpleName();
    @BindView(R.id.track_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_circular)
    ProgressBar progressBar;
    @BindView(R.id.album_name)
    TextView albumName;
    @BindView(R.id.artist_name)
    TextView artistName;
    @BindView(R.id.tracks_count)
    TextView trackCount;
    @BindView(R.id.album_image)
    ImageView albumImage;
    TrackAdapter mAdapter;
    @BindView(R.id.action_save)
    ImageView saveImageView;
    // App Database reference
    private AppDatabase mDb;

    private boolean itemExist = false;
    AlbumDetailsViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mAdapter = new TrackAdapter(this);
        mDb = AppDatabase.getsInstance(getApplicationContext());
        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        if (getIntent() != null) {
            if (getIntent().hasExtra(ARTIST_NAME) && getIntent().hasExtra(ALBUM_NAME)) {
                String artistName = getIntent().getStringExtra(ARTIST_NAME);
                String artistAlbum = getIntent().getStringExtra(ALBUM_NAME);
                displayProgress();
                //getAlbumInfo(artistName, artistAlbum);
                AlbumDetailsViewModelFactory factory =
                        new AlbumDetailsViewModelFactory(getApplication(), artistAlbum, artistName, getIntent().getBooleanExtra(ALBUM_FROM_DB, false));
                mViewModel = ViewModelProviders.of(this, factory)
                        .get(AlbumDetailsViewModel.class);
                mViewModel.getAlbumLiveData().observe(this, album -> {
                    if (mViewModel.isDataFromDb()) {
                        setItemExist(true);
                    } else {
                        //
                        LiveData<Album> data = mDb.albumDao().getAlbum(album.getName(), album.getArtistName());
                        data.observe(this, albumData -> {
                            if (albumData == null) {
                                setItemExist(false);
                            } else {
                                setItemExist(true);
                            }
                        });
                    }
                    if (album == null) {
                        // Close activity if there is no detail information about this album
                        finish();
                    } else {
                        setData(album);
                        displayContent();
                    }

                });
            }
        }
    }

    /**
     * Display the progress bar
     */
    void displayProgress() {
        progressBar.setVisibility(View.VISIBLE);
        albumImage.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        albumName.setVisibility(View.GONE);
        artistName.setVisibility(View.GONE);
        trackCount.setVisibility(View.GONE);
        saveImageView.setVisibility(View.GONE);
    }

    /**
     * Display the content of the search results
     */
    void displayContent() {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        albumName.setVisibility(View.VISIBLE);
        artistName.setVisibility(View.VISIBLE);
        trackCount.setVisibility(View.VISIBLE);
        albumImage.setVisibility(View.VISIBLE);
        saveImageView.setVisibility(View.VISIBLE);
    }

    void setData(@NonNull Album album) {
        Glide
                .with(this)
                .load(album.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.placeholder_artist_image)
                .error(R.drawable.placeholder_artist_image)
                .into(albumImage);

        albumName.setText(album.getName());
        artistName.setText(album.getArtistName());
        trackCount.setText(String.format(Locale.getDefault(), "%d %s", album.getTracks().size(), getString(R.string.tracks)));
        mAdapter.setData(album.getTracks());

        saveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the album locally
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (itemExist) {
                            mDb.albumDao().delete(album);
                        } else {
                            mDb.albumDao().insert(album);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (itemExist) {
                                    Toast.makeText(DetailActivity.this, R.string.album_deleted_successfully, Toast.LENGTH_LONG).show();
                                    if (mViewModel.isDataFromDb()) {
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(DetailActivity.this, R.string.album_saved_successfully, Toast.LENGTH_LONG).show();
                                }
                                setItemExist(!itemExist);
                            }
                        });
                    }
                });
            }
        });
    }

    void setItemExist(boolean exist) {
        itemExist = exist;
        if (itemExist) {
            saveImageView.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_action_delete));
        } else {
            saveImageView.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_action_save));
        }
    }

    void getAlbumInfo(String artistName, String albumName) {
        Call<Album> call = ApiClient.getClient().create(ApiInterface.class).getAlbumDetails(artistName, albumName, Utility.getApiKey());
        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                Log.d(TAG, response.body().toString());
                setData(response.body());
                displayContent();
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                Toast.makeText(DetailActivity.this, "Timeout", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
