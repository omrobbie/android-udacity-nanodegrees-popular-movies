package com.omrobbie.popmovies.restapi;

import com.omrobbie.popmovies.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APICall {

    @GET("movie/popular?")
    Call<MovieResponse> getPopularMovie(@Query("page") int page);

    @GET("movie/top_rated?")
    Call<MovieResponse> getHighRatedMovie(@Query("page") int page);
}
