package com.prototype48.michael.famous_movies.fragment;

import android.content.ContentProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.prototype48.michael.famous_movies.R;
import com.prototype48.michael.famous_movies.adapter.MovieTrailerAdapter;
import com.prototype48.michael.famous_movies.interfaces.AsyncTaskDelegate;
import com.prototype48.michael.famous_movies.interfaces.MovieFragmentInterface;
import com.prototype48.michael.famous_movies.model.Movie;
import com.prototype48.michael.famous_movies.model.MovieTrailer;
import com.prototype48.michael.famous_movies.service.MovieService;
import com.prototype48.michael.famous_movies.utils.MovieUtils;

import java.util.ArrayList;

public class MovieDetailsTrailerFragment extends Fragment implements MovieFragmentInterface,AsyncTaskDelegate {

    private static final String TITLE  = "Trailers";

    public static final String TAG_MOVIE = "movie";

    Movie mMovie;

    MovieService movieService;

    // layout views
    RecyclerView mMovieTrailers;

    ImageView mBackgroundImage;

    MovieUtils mMovieUtils;

    ArrayList<MovieTrailer> movieTrailers;


    @Override
    public Fragment create(Movie movie) {
        mMovie = movie;

        return  this;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(TAG_MOVIE,mMovie);
        super.onSaveInstanceState(outState);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null){
            this.mMovie = savedInstanceState.getParcelable(TAG_MOVIE);
        }


        this.movieService = new MovieService(this.getContext(),this,mMovie.getId());

        View v = inflater.inflate(R.layout.fragment_movie_trailer,container,false);

        mBackgroundImage = v.findViewById(R.id.img_background);

        mMovieUtils = new MovieUtils();

        String urlBackGround = mMovieUtils.createPosterPath(mMovie,true);

        mMovieUtils.loadPosterImageView(this.getContext(),mBackgroundImage,urlBackGround);

        movieService.execute("videos");

        mMovieTrailers = v.findViewById(R.id.rvMovieTrailers);


        return v;
    }

    @Override
    public void afterPost(Object output) {

        movieTrailers  = (ArrayList<MovieTrailer>) output;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        mMovieTrailers.setLayoutManager(layoutManager);

        MovieTrailerAdapter adapter;

        mMovieTrailers.setAdapter(null);

        adapter = new MovieTrailerAdapter(getContext());

        adapter.setmMovieTrailers(movieTrailers);

        mMovieTrailers.setAdapter(adapter);



    }

    @Override
    public String getFragmentTitle() {
        return TITLE;
    };



}
