package com.maca.andres.moviesproject.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.maca.andres.moviesproject.di.key.ViewModelKey;
import com.maca.andres.moviesproject.viewmodels.FactoryViewModel;
import com.maca.andres.moviesproject.viewmodels.PopularMoviesViewModel;
import com.maca.andres.moviesproject.viewmodels.SearchViewModel;
import com.maca.andres.moviesproject.viewmodels.TopMoviesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TopMoviesViewModel.class)
    abstract ViewModel bindsTopMovieViewModel(TopMoviesViewModel moviesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PopularMoviesViewModel.class)
    abstract ViewModel bindsPopularMovieViewModel(PopularMoviesViewModel moviesViewModel);
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindsSearchViewModel(SearchViewModel moviesViewModel);
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);

}
