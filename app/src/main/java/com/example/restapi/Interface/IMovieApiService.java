package com.example.restapi.Interface;

import com.example.restapi.Model.Result;
import com.example.restapi.Model.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMovieApiService {

    @GET("movie/top_rated")
    Call<Root> getTopRatedMovies (@Query("api_key") String apiKey);
}
