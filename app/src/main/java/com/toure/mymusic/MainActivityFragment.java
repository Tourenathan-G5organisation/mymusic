package com.toure.mymusic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toure.mymusic.adapter.MainScreenAdapter;
import com.toure.mymusic.util.ItemOffsetDecoration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment implements OnClickAlbumHandler {

    private MainActivityViewModel mViewModel;

    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    @BindView(R.id.album_RecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.messageTextview)
    TextView messageTextView;
    private MainScreenAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_activity_fragment, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new MainScreenAdapter(getActivity(), this);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mViewModel.getAlbums().observe(this, albums -> {
            mAdapter.submitList(albums);

            if (mAdapter.getItemCount() > 0) {
                Log.d("Main", "albums: " + albums.size());
                messageTextView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            } else {
                messageTextView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(String artistName, String albumName) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.ARTIST_NAME, artistName);
        intent.putExtra(DetailActivity.ALBUM_NAME, albumName);
        intent.putExtra(DetailActivity.ALBUM_FROM_DB, true);
        startActivity(intent);
    }
}
