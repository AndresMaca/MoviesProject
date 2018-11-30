package com.maca.andres.moviesproject.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(primaryKeys =
        {"category", "page"})
public class MoviePage {
    @NonNull
    @ColumnInfo(name = "category")
    private String category;
    @NonNull
    @ColumnInfo(name = "page")
    private int page;
    private List<Integer> moviesIds;

    public MoviePage(int page, String category, List<Integer> moviesIds) {
        this.page = page;
        this.category = category;
        this.moviesIds = moviesIds;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Integer> getMoviesIds() {
        return moviesIds;
    }

    public void setMoviesIds(List<Integer> moviesIds) {
        this.moviesIds = moviesIds;
    }
}
