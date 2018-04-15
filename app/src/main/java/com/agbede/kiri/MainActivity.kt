package com.agbede.kiri

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.agbede.kiri.models.NowPlayingMovies
import com.agbede.kiri.services.MovieService
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity(), retrofit2.Callback<NowPlayingMovies> {
    override fun onResponse(call: Call<NowPlayingMovies>?, response: Response<NowPlayingMovies>?) {
        Log.d("Response", response?.body()?.toString())
    }

    override fun onFailure(call: Call<NowPlayingMovies>?, t: Throwable?) {
        Log.d("Failure", t.toString())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api_key = getString(R.string.api_key)

        MovieService.Factory.getService().getNowPlayingMovies(api_key, 1).enqueue(this)
    }
}
