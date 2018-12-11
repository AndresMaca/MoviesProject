package com.maca.andres.moviesproject.repository;

import com.maca.andres.moviesproject.database.entity.Movie;

import java.util.List;

public interface SearchSubject {
    void registerSearchObserver(SearchObserver searchObserver);
    void deleteSearchObserver();
    void notifyToSearchObservers(List<Movie> movies);
}
