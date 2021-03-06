package com.maca.andres.moviesproject.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.devutils.LoggerDebug;
import com.maca.andres.moviesproject.repository.MovieRepository;
import com.maca.andres.moviesproject.repository.NewMovieObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static com.maca.andres.moviesproject.api.NetworkApiFields.UPCOMING;

public class UpcomingMoviesViewModel extends ViewModel implements NewMovieObserver {
    private static final String TAG = UpcomingMoviesViewModel.class.getSimpleName();


    private MutableLiveData<List<Movie>> movieListPopular;
    private int currentPage = 1;
    private MovieRepository movieRepository;
    protected String CATEGORY; //HINT: if you can simplify remember your protected modifiers

    @Inject
    public UpcomingMoviesViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.CATEGORY = UPCOMING;
        movieRepository.register(this, CATEGORY); //Attaching observer to Repository.
        init();

    }

    private void init() {
        LoggerDebug.print(TAG, "Popular Movies View Model Initialized");
        if (movieListPopular == null) {
            movieListPopular = new MutableLiveData<>();
            movieListPopular.setValue(new ArrayList<>());
            movieRepository.loadMoviesFromApi(CATEGORY, currentPage);

        }

    }


    @Override
    public void update(Movie movie, String category) {
        LoggerDebug.print(TAG, ".... getting new movie from repo");
        LoggerDebug.print(TAG, "movie title: " + movie.getTitle());
        addMovieFromRepo(movie, category);

    }

    private void addMovieFromRepo(Movie movie, String category) {//
        if (category.equals(CATEGORY)) {
            addMovieToLiveData(movieListPopular, movie);
            LoggerDebug.print(TAG, "Adding movie to UPCOMING List");
        }
    }

    private void addMovieToLiveData(MutableLiveData<List<Movie>> movieListMutableLiveData, Movie movie) {
        Objects.requireNonNull(movieListMutableLiveData.getValue()).add(movie);
        movieListMutableLiveData.postValue(movieListMutableLiveData.getValue()); //Because the thread are sending in background thread
    }


    public MutableLiveData<List<Movie>> getMovieListUpcoming() {
        return movieListPopular;
    }

    @Override
    protected void onCleared() { //Observer is not alive anymore
        super.onCleared();
        movieRepository.delete(CATEGORY);
    }

    public void getNextChunckOfData() {
        currentPage++;
        LoggerDebug.print(TAG, "Getting next Page");
        movieRepository.loadMoviesFromApi(CATEGORY, currentPage);
    }

}

