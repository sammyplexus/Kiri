package com.agbede.kiri.services;

import com.agbede.kiri.models.NowPlayingMovies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("now_playing")
    Call<NowPlayingMovies> getNowPlayingMovies(@Query("api_key") String api_key, @Query("page") int pageNumber);

    class Factory {
        public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w300/";

        public static MovieService getService(){
            String BASE_URL = "https://api.themoviedb.org/3/movie/";


            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

            MovieService movieService = retrofit.create(MovieService.class);
            return movieService;
        }
    }
}
