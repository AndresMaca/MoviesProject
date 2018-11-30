package com.maca.andres.moviesproject.repository;

import com.maca.andres.moviesproject.database.entity.Movie;

public interface NewMovieSubject {
    void register(NewMovieObserver newMovieObserver, String Name);

    void delete(String Name);

    void notifyNewMovieObserver(Movie movie, String category);

}
