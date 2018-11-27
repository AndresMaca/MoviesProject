package com.maca.andres.moviesproject.di.components;

import android.app.Application;

import com.maca.andres.moviesproject.MovieApp;
import com.maca.andres.moviesproject.di.module.ActivityModule;
import com.maca.andres.moviesproject.di.module.AppModule;
import com.maca.andres.moviesproject.di.module.FragmentModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ActivityModule.class, FragmentModule.class, AppModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
    void inject(MovieApp app);

}
