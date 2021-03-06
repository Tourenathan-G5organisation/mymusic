package com.toure.mymusic;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.toure.mymusic.adapter.ArtistSearchAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment implements OnClickSearchItemListerner {

    private static final String TAG = SearchFragment.class.getSimpleName();

    private SearchViewModel mViewModel;
    @BindView(R.id.artist_recyclerview)
    RecyclerView mRecyclerView;
    private ArtistSearchAdapter mAdapter;
    @BindView(R.id.progress_circular)
    ProgressBar progressBar;
    @BindView(R.id.comment)
    TextView mComment;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, rootView);
        mAdapter = new ArtistSearchAdapter(getContext(), this);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        mViewModel.getArtistLiveData().observe(this, artistPagedList -> {
            mAdapter.submitList(artistPagedList);
        });

    }


    /**
     * Display the progress bar
     */
    void displayProgress() {
        progressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mComment.setVisibility(View.GONE);
    }

    /**
     * Display the content of the search results
     */
    void displayContent() {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mComment.setVisibility(View.GONE);
    }

    void displayErrorMsg(String searchArtistName) {
        String text = getString(R.string.no_item_msg, TextUtils.htmlEncode(searchArtistName));
        progressBar.setVisibility(View.GONE);
        mComment.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mComment.setText(Html.fromHtml(text));
    }

    void displayErrorMsg() {
        progressBar.setVisibility(View.GONE);
        mComment.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mComment.setText(R.string.error_occurred);
    }

    @Override
    public void onClick(String artistName) {
        Intent intent = new Intent(getContext(), BestAlbumActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, artistName);
        startActivity(intent);
    }
}
