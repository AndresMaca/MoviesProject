package com.maca.andres.moviesproject.repository;

import com.maca.andres.moviesproject.database.entity.Movie;

public interface NewMovieObserver {
    void update(Movie movie, String category);
}
