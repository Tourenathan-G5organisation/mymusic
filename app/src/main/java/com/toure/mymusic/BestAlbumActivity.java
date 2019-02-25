package com.toure.mymusic;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.toure.mymusic.adapter.BestArtistAlbumsAdapter;
import com.toure.mymusic.util.ItemOffsetDecoration;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BestAlbumActivity extends AppCompatActivity {

    private static final String TAG = BestAlbumActivity.class.getSimpleName();

    @BindView(R.id.album_RecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_circular)
    ProgressBar progressBar;
    BestArtistAlbumsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_album);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mAdapter = new BestArtistAlbumsAdapter(this);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        displayContent();
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

}
