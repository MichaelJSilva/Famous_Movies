package com.prototype48.michael.famous_movies.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.prototype48.michael.famous_movies.R;
import com.prototype48.michael.famous_movies.adapter.MovieReviewAdapter;
import com.prototype48.michael.famous_movies.adapter.MovieTrailerAdapter;
import com.prototype48.michael.famous_movies.interfaces.AsyncTaskDelegate;
import com.prototype48.michael.famous_movies.interfaces.MovieFragmentInterface;
import com.prototype48.michael.famous_movies.model.Movie;
import com.prototype48.michael.famous_movies.model.MovieReview;
import com.prototype48.michael.famous_movies.service.MovieService;
import com.prototype48.michael.famous_movies.utils.MovieUtils;

import java.util.ArrayList;

public class MovieDetailsReviewsFragment extends Fragment implements MovieFragmentInterface,AsyncTaskDelegate {

    private static final String TITLE    = "Reviews";

    public static final String TAG_MOVIE = "movie";

    Movie mMovie;

    MovieService mMovieService;

    RecyclerView mMovieReview;

    MovieUtils  mMovieUtils;

    ImageView  mBackgroundImage;

    ArrayList<MovieReview> movieReviews;


    @Override
    public Fragment create(Movie movie) {
        this.mMovie = movie;
        return this;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(TAG_MOVIE,mMovie);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String urlBackGround;
        String urlPoster;

        if (savedInstanceState != null){
            this.mMovie = savedInstanceState.getParcelable(TAG_MOVIE);
        }

        this.mMovieService = new MovieService(this.getContext(),this,mMovie.getId());

        View v = inflater.inflate(R.layout.fragment_movie_reviews,container,false);

        mMovieService.execute("reviews");

        mMovieReview = v.findViewById(R.id.rvMovieReviews);

        mBackgroundImage = v.findViewById(R.id.img_background);

        mMovieUtils = new MovieUtils();

        urlBackGround = mMovieUtils.createPosterPath(mMovie,true);

        mMovieUtils.loadPosterImageView(this.getContext(),mBackgroundImage,urlBackGround,true);




        return v;
    }

    @Override
    public void afterPost(Object output) {
        if (output != null){
             movieReviews  = (ArrayList<MovieReview>) output;

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

            mMovieReview.setLayoutManager(layoutManager);

            MovieReviewAdapter adapter;

            mMovieReview.setAdapter(null);

            adapter = new MovieReviewAdapter(getContext());

            adapter.setmMoviesReviews(movieReviews);

            mMovieReview.setAdapter(adapter);


        }
    }

    @Override
    public String getFragmentTitle() {
        return TITLE;
    };

}
