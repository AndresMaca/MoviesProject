package com.maca.andres.moviesproject.di.module;

import com.maca.andres.moviesproject.activities.MainActivity;
import com.maca.andres.moviesproject.activities.SearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();
    @ContributesAndroidInjector(modules = FragmentSearchModule.class)
    abstract SearchActivity contributeSearchActivity();
}
