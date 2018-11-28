package com.maca.andres.moviesproject.repository;

import com.maca.andres.moviesproject.api.NetworkApi;
import com.maca.andres.moviesproject.database.dao.MovieDao;
import com.maca.andres.moviesproject.database.entity.ApiResponse;
import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.devutils.LoggerDebug;

import java.util.ArrayList;
import java.util.HashMap;
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
    //  private List<NewMovieObserver> newMovieObservers;
    private HashMap<String, NewMovieObserver> newMovieObservers;


    private List<Movie> initialTopRatedMovies;
    private List<Integer> initialKeys;
    private List<Movie> initialPopularMovies;


    @Inject
    public MovieRepository(Executor executor, MovieDao movieDao, NetworkApi networkApi) {
        this.executor = executor;
        this.movieDao = movieDao;
        this.networkApi = networkApi;
        initialTopRatedMovies = new ArrayList<>();
        initialPopularMovies = new ArrayList<>();
        loadInitialData();
        newMovieObservers = new HashMap<>();
        initialKeys = new ArrayList<>();
        loadMoviesFromApi(TOP_RATED);
        loadMoviesFromApi(POPULAR);
        LoggerDebug.print(TAG, "Repostory --created");
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
            //Add initial keys
        });
    }

    public void loadMoviesFromApi(String category) {
        executor.execute(() -> networkApi.getMoviesFromApiByCat(category, API_KEY).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                //Getting initial data. we're going to check what movies we already passed to the observers
                ApiResponse apiResponse = response.body();

                LoggerDebug.print(TAG, "URL " + call.request().url().toString());

                LoggerDebug.print(TAG, "Code" + response.code());
                if (response.code() == 200) {
                    LoggerDebug.print(TAG, "Network Repose Size: " + apiResponse.getResults().size());

                    for (int i = 0; i < apiResponse.getResults().size(); i++) {
                        //DEBUG como hacer que no se repita con los resultados obtenidos.
                        LoggerDebug.print(TAG, "Iteration: " + i);
                        apiResponse.getResults().get(i).setCategory(category);
                        //TODO Implementar mejor tecnicas.
                        int finalI = i;
                        executor.execute(() -> movieDao.saveMovie(apiResponse.getResults().get(finalI))); //Save it just for update keys like vote count and popularity. It can be easily omitted checking
                        // if the movie are already in the database with movieDao.loadMovie(movieid);
                        LoggerDebug.print(TAG, "Movie Saved!"); //TODO COMO putas hago que esta mierda no se envie dos veces!!! primera es cuando la carga de la base de datos la segunda es cuando llegan de internet.
                        if (!(initialKeys.contains(apiResponse.getResults().get(i).getId()))) { //The observers doesnt contains this movie! Sending to All.
                            LoggerDebug.print(TAG, "Doesn't contains this movie, notifying observers");
                            notifyNewMovieObserver(apiResponse.getResults().get(i), category);

                        }else{
                            LoggerDebug.print(TAG,"Movie Already in the observer");
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        }));


    }

    @Override
    public void register(NewMovieObserver newMovieObserver, String name) {
        LoggerDebug.print(TAG, "Observer Added");
        if (!newMovieObservers.containsKey(name)) {
            newMovieObservers.put(name, newMovieObserver);
        }
        LoggerDebug.print(TAG, "Observer number: " + newMovieObservers.size());


    }

    @Override
    public void delete(String name) {
        newMovieObservers.remove(name);

    }

    @Override
    public void notifyNewMovieObserver(Movie movie, String category) {
        LoggerDebug.print(TAG, "Observers: " + newMovieObservers.size());
        if (newMovieObservers.containsKey(category))
            newMovieObservers.get(category).update(movie, category);


    }

    @Override
    public void notifyInitialMovies(List<Movie> movies, String category) {
        if (newMovieObservers.containsKey(category)) {
            newMovieObservers.get(category).loadInitialMovies(movies, category);
            for (int i = 0; i < movies.size(); i++) {
                initialKeys.add(movies.get(i).getId());

            }
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

    public List<Movie> getPopularMovies() {
        return initialPopularMovies;
    }

    public List<Movie> getTopRatedMovies() {
        return initialTopRatedMovies;
    }
}
