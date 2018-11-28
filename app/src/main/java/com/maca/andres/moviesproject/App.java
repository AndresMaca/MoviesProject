package com.maca.andres.moviesproject;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.maca.andres.moviesproject.devutils.LoggerDebug;
import com.maca.andres.moviesproject.di.components.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {
    private static final String TAG = App.class.getSimpleName();
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initDagger();
        context = getApplicationContext();
        LoggerDebug.print(TAG,"Oncreate --Activity ");
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }


    private void initDagger(){
        DaggerAppComponent.builder().application(this).build().inject(this);
    }
}
