package com.maca.andres.moviesproject.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.gson.GsonBuilder;
import com.maca.andres.moviesproject.api.NetworkApi;
import com.maca.andres.moviesproject.database.MovieLocalDatabase;
import com.maca.andres.moviesproject.database.dao.MovieDao;
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
public class AppModule {
    @Provides
    @Singleton
    MovieLocalDatabase provideDatabase(Application application) {
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
        return new MovieRepository(executor, movieDao, networkApi);

    }

    //Network injection
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).baseUrl(URL_BASE).build();
    }

    @Singleton
    @Provides
    NetworkApi provideNetworkApi(Retrofit retrofit) {
        return retrofit.create(NetworkApi.class);
    }
}
