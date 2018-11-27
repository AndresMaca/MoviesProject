package com.maca.andres.moviesproject.repository;

import com.maca.andres.moviesproject.api.NetworkApi;
import com.maca.andres.moviesproject.database.dao.MovieDao;
import com.maca.andres.moviesproject.database.entity.ApiResponse;
import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.devutils.LoggerDebug;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.maca.andres.moviesproject.api.NetworkApiFields.API_KEY;
import static com.maca.andres.moviesproject.api.NetworkApiFields.POPULAR;
import static com.maca.andres.moviesproject.api.NetworkApiFields.TOP_RATED;

/**
 * This repo contains the data for the app operation, first offline, Repository class is the only class
 * when the data is operated.
 * First checks the local database (ROOM) and after that checks online API (retrofit2).
 * The repo can send data to the ViewModels with the ObserverPattern. There's a bidirectional
 * communication always taking care about every class function.
 */
@Singleton
public class MovieRepository implements NewMovieSubject {
    private static final String TAG = MovieRepository.class.getSimpleName();
    private final Executor executor; //task like local database queries are made with the executor for dont load the UI thread
    private final MovieDao movieDao;
    private final NetworkApi networkApi;
    private List<NewMovieObserver> newMovieObservers;

    public List<Movie> getTopRatedMovies() {
        return initialTopRatedMovies;
    }

    private List<Movie> initialTopRatedMovies;
    private List<Movie> initialPopularMovies;


    @Inject
    public MovieRepository(Executor executor, MovieDao movieDao, NetworkApi networkApi) {
        this.executor = executor;
        this.movieDao = movieDao;
        this.networkApi = networkApi;
        initialTopRatedMovies = new ArrayList<>();
        initialPopularMovies = new ArrayList<>();
        loadInitialData();
        newMovieObservers = new ArrayList<>();
        loadMoviesFromApi(TOP_RATED);
    }

    /**
     * Loads local data when the user open the app. Query is send to all the ViewModel listening.
     */
    public void loadInitialData() {
        executor.execute(() -> {
            this.initialTopRatedMovies = movieDao.getAllMoviesBy(TOP_RATED);
            notifyInitialMovies(initialTopRatedMovies, TOP_RATED);
        });
        executor.execute(() -> {
            this.initialPopularMovies = movieDao.getAllMoviesBy(POPULAR);
            notifyInitialMovies(initialPopularMovies, POPULAR);
        });
    }

    public void loadMoviesFromApi(String category) {
        executor.execute(() -> networkApi.getMoviesFromApiByCat(category, API_KEY).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                List<Movie> tempList = getMovieList(category);//Getting initial data. we're going to check what movies we already passed to the observers
                 ApiResponse  apiResponse = response.body();
                for (int i = 0; i < apiResponse.getResults().size(); i++) {
                    //DEBUG como hacer que no se repita con los resultados obtenidos.
                    apiResponse.getResults().get(i).setCategory(category);
                    movieDao.saveMovie(apiResponse.getResults().get(i));
                    LoggerDebug.print(TAG, "Movie Saved!");
                    if (!tempList.contains(apiResponse.getResults().get(i))) { //The observers doesnt contains this movie! Sending to All.
                        LoggerDebug.print(TAG, "Doesnt contains movie, notifying observers");
                        notifyNewMovieObserver(apiResponse.getResults().get(i));

                    }

                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        }));


    }

    @Override
    public void register(NewMovieObserver newMovieObserver) {
        if (!newMovieObservers.contains(newMovieObserver)) {
            newMovieObservers.add(newMovieObserver);
        }

    }

    @Override
    public void delete(NewMovieObserver newMovieObserver) {
        newMovieObservers.remove(newMovieObserver);

    }

    @Override
    public void notifyNewMovieObserver(Movie movie) {
        for (final NewMovieObserver newMovieObserver : newMovieObservers) {
            newMovieObserver.update(movie);
            LoggerDebug.print(TAG, "Sending Movie to observers...");
        }

    }

    @Override
    public void notifyInitialMovies(List<Movie> movies, String category) {
        for (final NewMovieObserver newMovieObserver : newMovieObservers) {
            newMovieObserver.loadInitialMovies(movies, category);
        }
    }

    private List<Movie> getMovieList(String category) {
        switch (category) {
            case TOP_RATED:
                return this.initialTopRatedMovies;
            case POPULAR:
                return this.initialPopularMovies;
            default:
                return this.initialPopularMovies;
        }
    }
}
