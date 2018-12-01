package com.maca.andres.moviesproject.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.devutils.LoggerDebug;
import com.maca.andres.moviesproject.repository.MovieRepository;
import com.maca.andres.moviesproject.repository.SearchObserver;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel implements SearchObserver {
    private static final String TAG = SearchViewModel.class.getSimpleName();
    private MovieRepository movieRepository;


    private MutableLiveData<List<Movie>> movieSearchResults;

    @Inject
    public SearchViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        movieRepository.registerSearchObserver(this);
        init();
    }

    private void init() {
        if (movieSearchResults == null) {
            movieSearchResults = new MutableLiveData<>();
            movieSearchResults.setValue(new ArrayList<>());
        }
    }

    public void searchMovie(String movieName) {
        movieRepository.searchMovie(movieName);
    }


    @Override
    public void update(List<Movie> movieList) {
        //Add movies to movieSearchResults
        LoggerDebug.print(TAG,"Movies: "+movieList.size());
        movieSearchResults.postValue(movieList);
    }

    public MutableLiveData<List<Movie>> getMovieSearchResults() {
        return movieSearchResults;
    }
}
