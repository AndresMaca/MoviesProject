package com.maca.andres.moviesproject.repository;

import com.maca.andres.moviesproject.database.entity.Movie;

import java.util.List;

public interface NewMovieSubject {
    void register(NewMovieObserver newMovieObserver);

    void delete(NewMovieObserver newMovieObserver);

    void notifyNewMovieObserver(Movie movie);

    void notifyInitialMovies(List<Movie> movies, String category);
}
