package com.maca.andres.moviesproject.di.module;

import android.arch.lifecycle.ViewModel;

import com.maca.andres.moviesproject.di.key.ViewModelKey;
import com.maca.andres.moviesproject.viewmodels.MoviesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel.class)
    abstract ViewModel bindsMovieViewModel(MoviesViewModel moviesViewModel);

}
