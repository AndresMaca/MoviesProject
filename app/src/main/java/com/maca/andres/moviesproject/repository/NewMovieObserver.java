package com.maca.andres.moviesproject.repository;

import com.maca.andres.moviesproject.database.entity.Movie;

import java.util.List;

public interface NewMovieObserver {
    void update(Movie movie);
    void loadInitialMovies(List<Movie> movies, String Category);
}
