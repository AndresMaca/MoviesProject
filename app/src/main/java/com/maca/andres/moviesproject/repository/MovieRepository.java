package com.maca.andres.moviesproject.repository;

import com.maca.andres.moviesproject.api.NetworkApi;
import com.maca.andres.moviesproject.database.dao.MovieDao;
import com.maca.andres.moviesproject.database.entity.ApiResponse;
import com.maca.andres.moviesproject.database.entity.Movie;
import com.maca.andres.moviesproject.database.entity.MoviePage;
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

/**
 * This repo contains the data for the app operation, Repository class is the only class called
 * when the data is operated.
 * First checks the local database (ROOM) and after that checks online API (retrofit2).
 * The repo can send data to the ViewModels with the ObserverPattern. There's a bidirectional
 * communication always taking care about every class function.
 *
 * Repository class should be called only by viewModel.
 *
 * 
 */
@Singleton
public class MovieRepository implements NewMovieSubject, SearchSubject {
    private static final String TAG = MovieRepository.class.getSimpleName();
    private final Executor executor; //task like local database queries are made with the executor for dont load the UI thread
    private final MovieDao movieDao;
    private final NetworkApi networkApi;
    private SearchObserver searchObserver;
    private HashMap<String, NewMovieObserver> newMovieObservers;




    @Inject
    public MovieRepository(Executor executor, MovieDao movieDao, NetworkApi networkApi) {
        this.executor = executor;
        this.movieDao = movieDao;
        this.networkApi = networkApi;
        newMovieObservers = new HashMap<>();
        LoggerDebug.print(TAG, "Repostory --created");
    }


    public void loadMoviesFromApi(String category, int page) {
        executor.execute(() -> networkApi.getMoviesFromApiByCatAndPage(category, API_KEY, page).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                //Getting initial data. we're going to check what movies we already passed to the observers
                ApiResponse apiResponse = response.body();
                LoggerDebug.print(TAG, "URL " + call.request().url().toString());
                LoggerDebug.print(TAG, "Code" + response.code());
                if (response.code() == 200) {
                    List<Integer> moviesIds = new ArrayList<>();

                    for (int i = 0; i < apiResponse.getResults().size(); i++) {
                        LoggerDebug.print(TAG, "Iteration: " + i);
                        apiResponse.getResults().get(i).setCategory(category);
                        int finalI = i;
                        executor.execute(() -> movieDao.saveMovie(apiResponse.getResults().get(finalI)));
                        moviesIds.add(apiResponse.getResults().get(i).getId());
                        //Save it just for update keys like vote count and popularity. It can be easily omitted checking
                        LoggerDebug.print(TAG, "Movie Saved!");
                        LoggerDebug.print(TAG, "notifying observers");
                        notifyToNewMovieObserver(apiResponse.getResults().get(i), category);


                    }
                    LoggerDebug.print(TAG, "Saving page with " + moviesIds.size() + "Number of movies, Category " + category + "page: " + page);
                    executor.execute(() -> movieDao.savePage(new MoviePage(page, category, moviesIds)));

                }

            }


            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                executor.execute(() -> {
                    LoggerDebug.print(TAG, "Cant Connect to Api" + t.getMessage());
                    LoggerDebug.print(TAG, "Quering cat: " + category + " page: " + page);
                    MoviePage moviePage = movieDao.loadMovieIdsByCategoryPage(category, page);
                    if (moviePage != null) {
                        LoggerDebug.print(TAG, "Movie Ids" + moviePage.getMoviesIds() + "MOVIES SIZE: " + moviePage.getMoviesIds().size());
                        for (int i = 0; i < moviePage.getMoviesIds().size(); i++) {
                            notifyToNewMovieObserver(movieDao.loadMovie(moviePage.getMoviesIds().get(i)), category);
                        }
                    } else {
                        LoggerDebug.print(TAG, "Unsuccessful query!");
                    }

                });


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
    public void notifyToNewMovieObserver(Movie movie, String category) {
        LoggerDebug.print(TAG, "Observers: " + newMovieObservers.size());
        if (newMovieObservers.containsKey(category))
            newMovieObservers.get(category).update(movie, category);


    }


    @Override
    public void registerSearchObserver(SearchObserver searchObserver) {
        this.searchObserver = searchObserver;
    }

    @Override
    public void deleteSearchObserver() {
        if (searchObserver != null) searchObserver = null;
    }

    @Override
    public void notifyToSearchObservers(List<Movie> movies) {
        //Get from query
        if (searchObserver != null) searchObserver.update(movies);

    }

    public void searchMovie(String movieName) {
        executor.execute(() -> networkApi.searchMovieOnline(API_KEY, movieName).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                LoggerDebug.print(TAG, "URL " + call.request().url().toString());
                ApiResponse apiResponse = response.body();
                if (apiResponse != null)
                    notifyToSearchObservers(apiResponse.getResults());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                executor.execute(() -> {
                    LoggerDebug.print(TAG, "Searching movie: " + movieName.substring(0, 1).toUpperCase() + movieName.substring(1));
                    List<Movie> movies = movieDao.searchMovie(movieName.substring(0, 1).toUpperCase() + movieName.substring(1));
                    if (movies != null) notifyToSearchObservers(movies);
                });

            }
        }));

    }
}
