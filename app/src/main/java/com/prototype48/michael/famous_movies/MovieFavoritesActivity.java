package com.prototype48.michael.famous_movies;

import android.database.Cursor;
import android.support.design.circularreveal.CircularRevealWidget;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.prototype48.michael.famous_movies.adapter.MovieCursorAdapter;
import com.prototype48.michael.famous_movies.model.Movie;
import com.prototype48.michael.famous_movies.utils.MovieDatabaseUtils;

import java.util.ArrayList;

public class MovieFavoritesActivity extends AppCompatActivity {


    RecyclerView mFavoriteMoviesList;

    MovieCursorAdapter mMovieCursorAdapter;

    Cursor mFavoritesCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_favorites);

        mFavoriteMoviesList  = (RecyclerView) findViewById(R.id.rcvFavoriteMovies);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mFavoriteMoviesList.setLayoutManager(layoutManager);

        loadFavoriteMovieList();



    }


    public void loadFavoriteMovieList(){

        MovieDatabaseUtils movieDatabaseUtils = new MovieDatabaseUtils(this);

        mFavoritesCursor = movieDatabaseUtils.findAll();

        Log.d("contador", String.valueOf(mFavoritesCursor.getCount()));

        mMovieCursorAdapter = new MovieCursorAdapter(this);

        mMovieCursorAdapter.setmCursor(mFavoritesCursor);

        mFavoriteMoviesList.setAdapter(mMovieCursorAdapter);

        mMovieCursorAdapter.notifyDataSetChanged();




    }

}
