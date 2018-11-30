package com.maca.andres.moviesproject.viewmodels;

import com.maca.andres.moviesproject.repository.MovieRepository;

import static com.maca.andres.moviesproject.api.NetworkApiFields.TOP_RATED;

public class TopMovieViewModel extends PopularMoviesViewModel{

    public TopMovieViewModel(MovieRepository movieRepository) {

        super(movieRepository);
        super.CATEGORY = TOP_RATED;

    }
}
