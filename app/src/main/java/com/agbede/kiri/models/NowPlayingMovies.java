package com.agbede.kiri.models;

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
    ArrayList<Results> results;

    class Results {

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

        public Results(String title, String poster_path, double vote_average, String overview, String release_date) {
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
}
