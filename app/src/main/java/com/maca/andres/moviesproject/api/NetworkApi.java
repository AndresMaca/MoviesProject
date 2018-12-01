package com.maca.andres.moviesproject.api;

import com.maca.andres.moviesproject.database.entity.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkApi {
    @GET("/3/movie/{category}") //TODO quitar el /3/
    Call<ApiResponse> getMoviesFromApiByCat(@Path("category") String category, @Query("api_key") String apiKey);

    @GET("/3/movie/{category}") //TODO quitar el /3/
    Call<ApiResponse> getMoviesFromApiByCatAndPage(@Path("category") String category, @Query("api_key") String apiKey,@Query("page") int page);

    @GET("/3/search/movie")
    Call<ApiResponse> searchMovieOnline(@Query("api_key") String api_key, @Query("query") String movie_query);
}
