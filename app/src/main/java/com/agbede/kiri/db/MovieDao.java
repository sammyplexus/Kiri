package com.agbede.kiri.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.agbede.kiri.models.Results;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewMovie(Results movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewMovies(List<Results> movies);

    @Query("SELECT * FROM Results")
    LiveData<List<Results>> getAllMovies();

    @Query("SELECT * FROM Results WHERE title LIKE :movieTitle")
    LiveData<List<Results>> getMoviesThatMatchSearchParameter(String movieTitle);
}
