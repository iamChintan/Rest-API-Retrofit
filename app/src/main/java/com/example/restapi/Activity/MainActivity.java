package com.example.restapi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;

import com.example.restapi.Adapter.MoviesAdapter;
import com.example.restapi.Interface.IMovieApiService;
import com.example.restapi.Model.Result;
import com.example.restapi.Model.Root;
import com.example.restapi.R;
import com.example.restapi.Utils.Common;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InIT();
    }

    private void InIT() {
        recyclerView  = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        GetAPI();
    }

    private void GetAPI() {

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Common.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        IMovieApiService movieApiService = retrofit.create(IMovieApiService.class);
        Call<Root> call = movieApiService.getTopRatedMovies(Common.API_KEY);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                List<Result> results = response.body().getResults();
                recyclerView.setAdapter( new MoviesAdapter(results, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}