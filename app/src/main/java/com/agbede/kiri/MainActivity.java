package com.agbede.kiri;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.agbede.kiri.db.MovieDao;
import com.agbede.kiri.db.MovieDb;
import com.agbede.kiri.models.NowPlayingMovies;
import com.agbede.kiri.models.Results;
import com.agbede.kiri.services.MovieService;
import com.github.ybq.android.spinkit.SpinKitView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.RecyclerViewClickListener{
    private String api_key;
    private RecyclerView mRecyclerView;
    private SpinKitView spinKitView;
    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private MovieAdapter movieAdapter;

    private ArrayList<Results> movies;
    Button button;

    private static final String BUNDLE_SAVE_STATE_RECYCLER_VIEW = "bundle_recycler_layout";

    private GridLayoutManager layoutManager;
    private MovieDao movieDao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinKitView = findViewById(R.id.spin_kit);
        searchView = findViewById(R.id.search_view);
        toolbar = findViewById(R.id.toolbar);
        movieDao = MovieDb.getDb(getApplicationContext()).getDao();

        movieDao.getAllMovies().observe(this, new Observer<List<Results>>() {
            @Override
            public void onChanged(@Nullable List<Results> results) {
                movies = (ArrayList<Results>) results;
                movieAdapter = new MovieAdapter((ArrayList<Results>) results, MainActivity.this);
                mRecyclerView.setAdapter(movieAdapter);
                spinKitView.setVisibility(View.GONE);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kiri");

        setUpRecyclerView();

        api_key = getString(R.string.api_key);

        MovieService service = MovieService.Factory.getService();

        service.getNowPlayingMovies(api_key, 1).enqueue(new Callback<NowPlayingMovies>() {
            @Override
            public void onResponse(Call<NowPlayingMovies> call, Response<NowPlayingMovies> response) {
                final NowPlayingMovies movies = response.body();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MovieDb.getDb(getApplicationContext()).getDao().insertNewMovies(movies.results);
                    }
                }).start();

            }

            @Override
            public void onFailure(Call<NowPlayingMovies> call, Throwable t) {
                Snackbar.make(mRecyclerView, "There is some error", Snackbar.LENGTH_LONG).show();
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Text", newText);
                processFilter(newText);
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.moviesRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void toggleRecyclerView(){
        //if current span count is 1, set the span count to 2 else set it to one.
        int span_count = ((GridLayoutManager)mRecyclerView.getLayoutManager()).getSpanCount();
        ((GridLayoutManager)mRecyclerView.getLayoutManager()).setSpanCount(span_count == 1 ? 2 : 1);

    }

    //On Screen rotation, save the state of the RecyclerView so that it can used when the screen orientation changes
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (layoutManager != null){
            outState.putParcelable(BUNDLE_SAVE_STATE_RECYCLER_VIEW, layoutManager.onSaveInstanceState());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(BUNDLE_SAVE_STATE_RECYCLER_VIEW)){
                Parcelable savedRecylerLayoutState = savedInstanceState.getParcelable(BUNDLE_SAVE_STATE_RECYCLER_VIEW);
                layoutManager.onRestoreInstanceState(savedRecylerLayoutState);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_toggle : toggleRecyclerView();
            return true;
            }
        return false;
    }

    private void processFilter(String newText) {
        if (movies != null){
            ArrayList<Results> filteredResults = new ArrayList<>();
            for (Results result : movies){
                if (result.getTitle().contains(newText)){
                    filteredResults.add(result);
                }
            }

            if (filteredResults != null && filteredResults.size() > 0){

            }
        }
    }


    @Override
    public void onClickItem(int position, View itemView) {
        Results results = (Results) itemView.getTag();
        String title = results.getTitle();
        String summary = results.getOverview();

        new AlertDialog.Builder(this).setTitle(title).setMessage(summary).create().show();
    }
}
