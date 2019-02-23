package com.toure.mymusic.data;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;

public class ArtistQuery {

    private Integer page;
    private Integer totalResults;
    private Integer itemsPerPage;
    private Integer startIndex;
    private List<Artist> artists;

    public ArtistQuery() {
    }

    public ArtistQuery(Integer page, Integer totalResults, Integer itemsPerPage, Integer startIndex, List<Artist> artists) {
        this.page = page;
        this.totalResults = totalResults;
        this.itemsPerPage = itemsPerPage;
        this.startIndex = startIndex;
        this.artists = artists;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Page: %d\ntotalResults: %d\nitemsPerPage: %d\nstartIndex: %d\n", page, totalResults, itemsPerPage, startIndex);
    }
}
