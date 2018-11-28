package com.maca.andres.moviesproject.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maca.andres.moviesproject.api.NetworkApi;
import com.maca.andres.moviesproject.database.MovieLocalDatabase;
import com.maca.andres.moviesproject.database.dao.MovieDao;
import com.maca.andres.moviesproject.devutils.LoggerDebug;
import com.maca.andres.moviesproject.repository.MovieRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.maca.andres.moviesproject.api.NetworkApiFields.URL_BASE;

@Module(includes = ViewModelModule.class)
public class AppModule2 {
    @Provides
    @Singleton
    MovieLocalDatabase provideDatabase(Application application) {
        Log.d("AppModule", "Database --AppModule");

        return Room.databaseBuilder(application, MovieLocalDatabase.class, "MovieDatabase.db").build();
    }

    @Provides
    @Singleton
    MovieDao providesMovieDao(MovieLocalDatabase movieLocalDatabase) {
        return movieLocalDatabase.movieDao();
    }

    @Provides
    Executor providesExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    MovieRepository providesMovieRepository(Executor executor, MovieDao movieDao, NetworkApi networkApi) {
        Log.d("AppModule", "Repository --AppModule");
        return new MovieRepository(executor, movieDao, networkApi);

    }
    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    //Network injection
    @Provides
    Retrofit provideRetrofit(Gson gson) {
        LoggerDebug.print("AppModule", "Retrofit --AppModule");
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(URL_BASE).build();
    }

    @Provides
    @Singleton
    NetworkApi provideNetworkApi(Retrofit retrofit) {
        return retrofit.create(NetworkApi.class);
    }
}
