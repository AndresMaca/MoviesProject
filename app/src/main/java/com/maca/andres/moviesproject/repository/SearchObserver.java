package com.maca.andres.moviesproject.repository;

import com.maca.andres.moviesproject.database.entity.Movie;

import java.util.List;

public interface SearchObserver {
    void update(List<Movie> movieList);
}
