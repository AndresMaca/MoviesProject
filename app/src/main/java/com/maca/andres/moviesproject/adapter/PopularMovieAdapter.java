package com.maca.andres.moviesproject.adapter;

import android.content.Context;

import com.maca.andres.moviesproject.database.entity.Movie;

import java.util.List;

public class PopularMovieAdapter extends MoviesAdapter{

    public PopularMovieAdapter(List<Movie> data, Context context) {
        super(data, context);
    }
    /*
    This is the popular movie adapter so im going to starred the movies with more than 3000.000 in popularity.
     */

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getPopularity()>300.000)
            return STARRED_MOVIE;
        else return NORMAL_MOVIE;
    }
}
