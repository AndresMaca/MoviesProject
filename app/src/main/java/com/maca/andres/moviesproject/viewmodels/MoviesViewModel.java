package com.maca.andres.moviesproject.viewmodels;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.devutils.LoggerDebug;
import com.maca.andres.moviesproject.repository.MovieRepository;
import com.maca.andres.moviesproject.repository.NewMovieObserver;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
This is the ViewModel Class, it has a scope to the fragments, it is useful for keep data up even if the user rotates the device.
 */
public class MoviesViewModel extends ViewModel implements NewMovieObserver {
    private static final String TAG = MoviesViewModel.class.getSimpleName();




    private MutableLiveData<List<Movie>> movieListTopRated;
    private MovieRepository movieRepository;

    @Inject
    public MoviesViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        init();

    }

    private void init() {
        LoggerDebug.print("Movies View Model Initialized", TAG);
        if(movieListTopRated == null){
            movieListTopRated = new MutableLiveData<>();
            if(movieRepository.getTopRatedMovies() != null){
                movieListTopRated.setValue(movieRepository.getTopRatedMovies());
            }else {
                movieListTopRated.setValue(new ArrayList<>());
            }
        }

    }


    @Override
    public void update(Movie movie) {
        LoggerDebug.print(TAG,".... getting new movie from repo");
        LoggerDebug.print(TAG, "movie title: "+movie.getTitle());

    }

    @Override
    public void loadInitialMovies(List<Movie> movies, String Category) {

    }

    public MutableLiveData<List<Movie>> getMovieListTopRated() {
        return movieListTopRated;
    }

}
