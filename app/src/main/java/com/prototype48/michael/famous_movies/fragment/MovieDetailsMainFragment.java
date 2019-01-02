package com.prototype48.michael.famous_movies.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.prototype48.michael.famous_movies.R;
import com.prototype48.michael.famous_movies.interfaces.MovieFragmentInterface;
import com.prototype48.michael.famous_movies.model.Movie;
import com.prototype48.michael.famous_movies.utils.MovieUtils;

public class MovieDetailsMainFragment extends Fragment implements MovieFragmentInterface {
    // const
    private static final String TITLE                      = "Movie Details";

    // tags
    final static String TAG_MOVIE        = "movie";
    final static String TAG_POSTER       = "poster";
    final static String TAG_BACKGROUND   = "bg";

    // glide options
    private RequestOptions optionsPoster = new RequestOptions();
    private RequestOptions optionsBg     = new RequestOptions();

    // var
    private static   Bundle   parameters;
    private static   Movie    mMovie;
    private MovieUtils mMovieUtils;

    // layout views
    private TextView    tvUserRating;
    private TextView    tvMovieTitle;
    private TextView    tvMovieOverview;
    private TextView    tvLaunchDate;
    private ImageView   tvPoster;
    private ImageView   tvImageBackground;

    public MovieDetailsMainFragment create(Movie movie){

        mMovie = movie;

        return  this;
    }

    @Override
    public String getFragmentTitle() {
        return TITLE;
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movie_details,container,false);

        tvUserRating       =  v.findViewById(R.id.tv_user_rating);
        tvMovieTitle       =  v.findViewById(R.id.details_movie_title);
        tvMovieOverview    =  v.findViewById(R.id.tv_overview);
        tvLaunchDate       =  v.findViewById(R.id.tv_launch_date);
        tvPoster           =  v.findViewById(R.id.imageView);
        tvImageBackground  =  v.findViewById(R.id.img_background);

        Movie movie = mMovie;

        tvMovieOverview.setMovementMethod(new ScrollingMovementMethod());

        String urlBackGround;
        String urlPoster;

        if (movie != null){

            tvUserRating.setText(String.valueOf(movie.getVoteAverage()));
            tvMovieTitle.setText(movie.getTitle());
            tvMovieOverview.setText(movie.getOverview());
            tvLaunchDate.setText(movie.getReleaseDate());

            mMovieUtils = new MovieUtils();

            urlBackGround = mMovieUtils.createPosterPath(movie,true);

            urlPoster = mMovieUtils.createPosterPath(movie,false);

            mMovieUtils.loadPosterImageView(this.getContext(),tvImageBackground,urlBackGround);

            mMovieUtils.loadPosterImageView(this.getContext(),tvPoster,urlPoster);



        }



        return v;
    }



}
