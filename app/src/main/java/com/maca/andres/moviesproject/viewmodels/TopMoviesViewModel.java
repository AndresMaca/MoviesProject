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

import static com.maca.andres.moviesproject.api.NetworkApiFields.TOP_RATED;

/*
This is the ViewModel Class, it has a scope to the fragments, it is useful for keep data up even if the user rotates the device.
In this case this is scoped from every fragment.
 */
public class TopMoviesViewModel extends ViewModel implements NewMovieObserver {
    private static final String TAG = TopMoviesViewModel.class.getSimpleName();


    private MutableLiveData<List<Movie>> movieListTopRated;

    private MovieRepository movieRepository;

    @Inject
    public TopMoviesViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        movieRepository.register(this,TOP_RATED); //Attaching observer to Repository.
        init();

    }

    private void init() {
        LoggerDebug.print(TAG, "Movies View Model Initialized");
        if (movieListTopRated == null) {
            movieListTopRated = new MutableLiveData<>();
            if (movieRepository.getTopRatedMovies() != null) { //Getting local stored data
                movieListTopRated.setValue(movieRepository.getTopRatedMovies());
            } else {
                movieListTopRated.setValue(new ArrayList<>());
            }
        }
    }


    @Override
    public void update(Movie movie, String category) {
        LoggerDebug.print(TAG, ".... getting new movie from repo");
        LoggerDebug.print(TAG, "movie title: " + movie.getTitle());
        addMovieFromRepo(movie, category);

    }

    private void addMovieFromRepo(Movie movie, String category) {//
        if(category.equals(TOP_RATED)){
            addMovieToLiveData(movieListTopRated, movie);
            LoggerDebug.print(TAG, "Adding movie to TopRated List");
        }
    }

    private void addMovieToLiveData(MutableLiveData<List<Movie>> movieListMutableLiveData, Movie movie) {
        Objects.requireNonNull(movieListMutableLiveData.getValue()).add(movie);
        movieListMutableLiveData.postValue(movieListMutableLiveData.getValue()); //Because the thread are sending in background thread
    }

    @Override
    public void loadInitialMovies(List<Movie> movies, String category) {
        if (category.equals(TOP_RATED)){
            movieListTopRated.postValue(movies);
        }

    }

    public MutableLiveData<List<Movie>> getMovieListTopRated() {
        return movieListTopRated;
    }

    @Override
    protected void onCleared() { //Observer is not alive anymore
        super.onCleared();
        movieRepository.delete(TOP_RATED);
    }
}
