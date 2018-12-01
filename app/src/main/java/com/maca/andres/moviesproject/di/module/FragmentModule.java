package com.maca.andres.moviesproject.di.module;

import com.maca.andres.moviesproject.Fragments.PopularMovieFragment;
import com.maca.andres.moviesproject.Fragments.TopMovieFragment;
import com.maca.andres.moviesproject.Fragments.UpcomingMovieFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract TopMovieFragment contributesTopMovieFragment();

    @ContributesAndroidInjector
    abstract PopularMovieFragment contributesPopularMovieFragment();

    @ContributesAndroidInjector
    abstract UpcomingMovieFragment contributesUpcomingMovieFragment();

}
