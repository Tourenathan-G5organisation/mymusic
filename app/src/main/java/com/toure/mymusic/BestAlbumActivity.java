package com.toure.mymusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.toure.mymusic.adapter.BestArtistAlbumsAdapter;
import com.toure.mymusic.util.ItemOffsetDecoration;

import java.util.Locale;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.toure.mymusic.BestAlbumViewModel.FAILURE;
import static com.toure.mymusic.BestAlbumViewModel.NO_RESULTS;
import static com.toure.mymusic.BestAlbumViewModel.SUCCESS;

public class BestAlbumActivity extends AppCompatActivity implements OnClickAlbumHandler {

    private static final String TAG = BestAlbumActivity.class.getSimpleName();

    @BindView(R.id.album_RecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_circular)
    ProgressBar progressBar;
    BestArtistAlbumsAdapter mAdapter;
    BestAlbumViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_album);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mAdapter = new BestArtistAlbumsAdapter(this, this);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        if (getIntent() != null && getIntent().hasExtra(Intent.EXTRA_TEXT)) {
            String artistName = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            setTitle(String.format(Locale.getDefault(), "%s %s", getString(R.string.title_activity_best_album), artistName));
            displayProgress();
            BestAlbumViewModelFactory factory = new BestAlbumViewModelFactory(artistName);
            viewModel = ViewModelProviders.of(this, factory).get(BestAlbumViewModel.class);
            mAdapter.setArtist(artistName);
            viewModel.getAlbumList().observe(this, albums -> mAdapter.submitList(albums));

            viewModel.getSearchStatus().observe(this, status -> {
                switch (status){
                    case SUCCESS:
                        displayContent();
                        break;
                    case FAILURE:
                        break;
                    case NO_RESULTS:
                        break;


                }
            });

        }
    }

    /**
     * Display the progress bar
     */
    void displayProgress() {
        progressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    /**
     * Display the content of the search results
     */
    void displayContent() {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }



    @Override
    public void onClick(String artistName, String albumName) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ARTIST_NAME, artistName);
        intent.putExtra(DetailActivity.ALBUM_NAME, albumName);
        startActivity(intent);
    }
}
