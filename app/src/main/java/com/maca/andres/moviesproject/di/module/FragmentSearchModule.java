package com.maca.andres.moviesproject.di.module;

import com.maca.andres.moviesproject.Fragments.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentSearchModule {
    @ContributesAndroidInjector
    abstract SearchFragment contributesSearchFragment();
}
