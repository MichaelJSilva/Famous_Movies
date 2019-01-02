package com.prototype48.michael.famous_movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.prototype48.michael.famous_movies.MovieDetailsActivity;
import com.prototype48.michael.famous_movies.R;
import com.prototype48.michael.famous_movies.model.Movie;
import com.prototype48.michael.famous_movies.utils.MovieDatabaseUtils;

import java.util.ArrayList;



public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    //baseURL
    private final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";
    private final String POSTER_BACKGROUND_SIZE_URL = "w185/";
    private final String POSTER_SCREEN_SIZE_URL     = "w500/";
    // glide options
    private final RequestOptions options = new RequestOptions();

    private ArrayList<Movie> mMovies;

    private final  Context context;

    private MovieDatabaseUtils movieDatabaseUtils;



    public MoviesAdapter(Context context) {

        this.context = context;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final ImageView   mMoviePoster;

        private MoviesViewHolder(View itemView) {
            super(itemView);
            mMoviePoster    =  itemView.findViewById(R.id.movie_poster);
            // set on click listenners

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            int viewID = v.getId();

            movieDatabaseUtils = new MovieDatabaseUtils(context);

            Movie movieClicked;

            movieClicked = mMovies.get(getAdapterPosition());


            openMovieDetail(movieClicked);


        }
    }




    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_movies_card,parent,false);

        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        // current movie
        Movie actualMovie = mMovies.get(position);
        // options of glider lib
        options.centerCrop();



        Glide.with(holder.mMoviePoster.getContext())
                .load(BASE_POSTER_URL + POSTER_BACKGROUND_SIZE_URL + actualMovie.getposterPath())
                .apply(options)
                .into(holder.mMoviePoster);

    }

    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();
        }else{
            return 0;
        }
    }

    private void openMovieDetail(Movie selectedMovie){

        Intent intent = new Intent(this.context,MovieDetailsActivity.class);

        intent.putExtra(MovieDetailsActivity.TAG_MOVIE,selectedMovie);

        intent.putExtra(MovieDetailsActivity.TAG_POSTER,BASE_POSTER_URL + POSTER_BACKGROUND_SIZE_URL + selectedMovie.getposterPath());

        intent.putExtra(MovieDetailsActivity.TAG_BG,BASE_POSTER_URL + POSTER_SCREEN_SIZE_URL + selectedMovie.getposterPath());

        context.startActivity(intent);


    }


    public ArrayList<Movie> getmMovies() {
        return mMovies;
    }

    public void setmMovies(ArrayList<Movie> mMovies) {
        this.mMovies = mMovies;
    }
}
