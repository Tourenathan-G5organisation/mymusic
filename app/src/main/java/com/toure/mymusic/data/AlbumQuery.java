package com.toure.mymusic.data;

import java.util.List;

public class AlbumQuery {

    private String artistName;
    private Integer page;
    private Integer itemsPerPage;
    private Integer totalPages;
    private Integer totalItems;
    private List<Album> albums;

    public AlbumQuery(String artistName, Integer page, Integer itemsPerPage, Integer totalPages, Integer totalItems,
                      List<Album> albums) {
        this.artistName = artistName;
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.albums = albums;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
