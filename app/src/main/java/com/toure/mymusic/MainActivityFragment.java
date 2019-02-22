package com.toure.mymusic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toure.mymusic.adapter.MainScreenAdapter;
import com.toure.mymusic.util.ItemOffsetDecoration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
    MainScreenAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_activity_fragment, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new MainScreenAdapter();
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        // TODO: Use the ViewModel
    }

}
