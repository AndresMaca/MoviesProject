package com.maca.andres.moviesproject.di.module;

import com.maca.andres.moviesproject.Fragments.TopMovieFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract TopMovieFragment contributesTopMovieFragment();
    

}
