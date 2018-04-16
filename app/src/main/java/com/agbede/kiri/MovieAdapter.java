package com.agbede.kiri;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agbede.kiri.models.NowPlayingMovies;
import com.agbede.kiri.models.Results;
import com.agbede.kiri.services.MovieService;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    interface RecyclerViewClickListener {
        void onClickItem(int position, View itemView);
    }

    ArrayList<Results> results;
    RecyclerViewClickListener listener;
    public MovieAdapter(ArrayList<Results> results, RecyclerViewClickListener listener){
        this.results = results;
        this.listener = listener;
    }

    public void replace(ArrayList<Results> results){
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.individual_movie_card, parent, false);

        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Results results = this.results.get(position);
        String title = results.getTitle();
        double rating = results.getVote_average();

        String poster_path = results.getPoster_path().substring(1);
        String image = MovieService.Factory.BASE_POSTER_URL + "" + poster_path;

        holder.movieTitle.setText(title);
        holder.movieRating.setText(String.format("Rating : %s", String.valueOf(rating)));

        holder.itemView.setTag(results);

        Glide.with(holder.itemView.getContext()).load(image).into(holder.moviePoster);

    }

    @Override
    public int getItemCount() {
        return results == null ? 0 :  results.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviePoster;
        TextView movieTitle;
        TextView movieRating;

        public MovieViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieRating = itemView.findViewById(R.id.movieRating);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClickItem(getAdapterPosition(), itemView);
        }
    }
}
