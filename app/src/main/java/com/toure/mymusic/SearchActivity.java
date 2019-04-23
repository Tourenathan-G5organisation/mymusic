package com.toure.mymusic;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.toure.mymusic.util.Utility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();
    SearchViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        mViewModel = ViewModelProviders.of(SearchActivity.this).get(SearchViewModel.class);
        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconified(false);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            if (Utility.isOnline(this)) {
                String query = intent.getStringExtra(SearchManager.QUERY);
                if (!query.isEmpty()) {
                    Log.d(TAG, "Search string: " + query);
                    SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                    searchFragment.displayProgress();
                    mViewModel.searchArtist(query);
                    mViewModel.getSearchStatus().observe(this, integer -> {
                        if (integer.equals(mViewModel.SUCCESS)){
                            searchFragment.displayContent();
                        }
                        else if (integer.equals(mViewModel.NO_RESULTS)){
                            searchFragment.displayErrorMsg(query);
                        }
                        else if (integer.equals(mViewModel.FAILURE)){
                            searchFragment.displayErrorMsg();
                        }
                    });

                }
            } else {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, R.string.no_internet_msg, Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
