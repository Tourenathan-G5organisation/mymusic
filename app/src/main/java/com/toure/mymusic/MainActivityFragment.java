package com.toure.mymusic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toure.mymusic.adapter.MainScreenAdapter;
import com.toure.mymusic.data.Album;
import com.toure.mymusic.data.AppDatabase;
import com.toure.mymusic.util.ItemOffsetDecoration;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment {

    private MainActivityViewModel mViewModel;

    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    @BindView(R.id.album_RecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.messageTextview)
    TextView messageTextView;
    MainScreenAdapter mAdapter;
    // App Database reference
    private AppDatabase mDb;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_activity_fragment, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new MainScreenAdapter(getActivity());
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mDb = AppDatabase.getsInstance(getActivity().getApplicationContext());
        mRecyclerView.setVisibility(View.GONE);
        LiveData<List<Album>> allAlbums = mDb.albumDao().getAllAlbums();
        allAlbums.observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> albums) {
                if (albums != null && albums.size() > 0) {
                    Log.d("Main", "albums: " + albums.size());
                    messageTextView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mAdapter.setData(albums);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        // TODO: Use the ViewModel
    }

}
