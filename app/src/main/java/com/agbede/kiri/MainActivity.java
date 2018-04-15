package com.agbede.kiri;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.agbede.kiri.models.NowPlayingMovies;
import com.agbede.kiri.services.MovieService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    private String api_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api_key = getString(R.string.api_key);

        MovieService service = MovieService.Factory.getService();
        service.getNowPlayingMovies(api_key, 1).enqueue(new Callback<NowPlayingMovies>() {
            @Override
            public void onResponse(Call<NowPlayingMovies> call, Response<NowPlayingMovies> response) {
                Log.d("Response", response.body().toString());
            }

            @Override
            public void onFailure(Call<NowPlayingMovies> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });

    }
}
