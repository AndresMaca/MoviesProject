package com.maca.andres.moviesproject.api;

import com.maca.andres.moviesproject.database.entity.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkApi {
    @GET("/movie/{category}")
    Call<ApiResponse> getMoviesFromApiByCat(String category, @Query("api_key") String apiKey);
}
