package com.prototype48.michael.famous_movies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prototype48.michael.famous_movies.adapter.MoviesAdapter;
import com.prototype48.michael.famous_movies.model.Movie;
import com.prototype48.michael.famous_movies.interfaces.AsyncTaskDelegate;
import com.prototype48.michael.famous_movies.service.MovieService;
import com.prototype48.michael.famous_movies.utils.MovieApiUtils;

import java.net.URL;
import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity implements AsyncTaskDelegate {

    private RecyclerView mMoviesList;

    private MoviesAdapter mMoviesAdapter;

    private ProgressBar mLoading;

    String clickedButton;

    private String mOrderBy;

    private static final String MOVIE_PATH_POPULAR = "Popular";

    private static final String MOVIE_PATH_TOPRATED = "Top Rated";

    private static final String MOVIE_VIEW_FAVORITES = "View Favorites";

    private static final String FAVORITE_MOVIES_TAG = "FavoriteMovies";

    private static final String LIST_ORDER_TAG = "Order";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mMoviesList =  findViewById(R.id.movie_list);

        mLoading    =  findViewById(R.id.pgb_loading);

        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(LIST_ORDER_TAG)){
                mOrderBy = savedInstanceState.getString(LIST_ORDER_TAG);
            }else{
                mOrderBy = MOVIE_PATH_POPULAR;
            }
        }else{
            mOrderBy = MOVIE_PATH_POPULAR;
        }

        loadMoviesList(mOrderBy);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.acitivy_movie_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(LIST_ORDER_TAG,mOrderBy);

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        clickedButton = item.getTitle().toString();

        mOrderBy = clickedButton;

        if (clickedButton.equals(MOVIE_PATH_TOPRATED)){
            loadMoviesList(MOVIE_PATH_TOPRATED);
        }else
            if (clickedButton.equals(MOVIE_PATH_POPULAR)){
                loadMoviesList(MOVIE_PATH_POPULAR);
        }else
            if(clickedButton.equals(MOVIE_VIEW_FAVORITES)){
                loadFavoritemovies();
            }

        return super.onOptionsItemSelected(item);
    }

    public void loadMoviesList(String path){


        // since there is no movie selected yet, no need to pass movieID
        MovieService movieService = new MovieService(getApplicationContext(),this,0);


        GridLayoutManager layoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);

        mMoviesList.setLayoutManager(layoutManager);

        if (connected(this)){
            movieService.execute(path);
        }else{
            Toast.makeText(this,R.string.offline,Toast.LENGTH_LONG).show();
        }

    }



    public boolean connected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo nInfo;

        if (connectivityManager != null){
            nInfo = connectivityManager.getActiveNetworkInfo();
            return nInfo.isConnected();
        }else{
            return  false;
        }
    }

    @Override
    public void afterPost(Object output) {

        if (output != null){

            ArrayList<Movie> movies = (ArrayList<Movie>) output;

            MovieService movieService = new MovieService(getApplicationContext(),this,0);


            mMoviesList.setAdapter(null);

            mMoviesAdapter = new MoviesAdapter(getApplicationContext());

            mMoviesAdapter.setmMovies(movies);

            mMoviesList.setAdapter(mMoviesAdapter);

            mMoviesAdapter.notifyDataSetChanged();

            mLoading.setVisibility(View.INVISIBLE);

        }
    }

    public void loadFavoritemovies(){

        Intent intent = new Intent(this,MovieFavoritesActivity.class);

        this.startActivity(intent);


    }


}

