package com.maca.andres.moviesproject.Fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.viewmodels.MoviesViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TopMovieFragment extends android.support.v4.app.Fragment {
    @Inject
    ViewModelProvider.Factory viewFactory;
    private MoviesViewModel moviesViewModel;

    public TopMovieFragment(){}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void configureViewModel(){
        AndroidSupportInjection.inject(this);
    }
    private void configureDagger(){
        moviesViewModel.getMovieListTopRated().observe(this, this::updateUI);
    }
    private void updateUI(List<Movie> movies){
        //updateAdapter();
    }
}
