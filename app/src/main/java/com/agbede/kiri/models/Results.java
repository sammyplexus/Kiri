package com.agbede.kiri.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity
public class Results {
    @PrimaryKey(autoGenerate = true)
    @Expose
    long id;

    @Expose
    String title;

    @Expose
    String poster_path;

    @Expose
    double vote_average;

    @Expose
    String overview;

    @Expose
    String release_date;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }



    public Results(long id, String title, String poster_path, double vote_average, String overview, String release_date) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;
    }

    @Override
    public String toString() {
        return "Results{" +
                "title='" + title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", vote_average=" + vote_average +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                '}';
    }
}
