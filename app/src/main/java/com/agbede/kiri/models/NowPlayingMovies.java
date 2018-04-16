package com.agbede.kiri.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;


public class NowPlayingMovies {

    @Override
    public String toString() {
        return "NowPlayingMovies{" +
                "results=" + results.toString() +
                '}';
    }


    @Expose
    public ArrayList<Results> results;


}
