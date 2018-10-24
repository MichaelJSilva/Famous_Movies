package com.prototype48.michael.famous_movies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.prototype48.michael.famous_movies.utils.MovieApiUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity {

    private RecyclerView mMoviesList;

    private MoviesAdapter mMoviesAdapter;

    private MovieApiUtils movieApiUtils;

    private ArrayList<Movie> movieResponse;

    private ProgressBar mLoading;

    private URL searchUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        movieApiUtils = new MovieApiUtils();

        mMoviesList =  findViewById(R.id.movie_list);

        mLoading    =  findViewById(R.id.pgb_loading);

        loadMoviesList("popular");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.acitivy_movie_menu,menu);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String clickedButton = item.getTitle().toString();

        if (clickedButton.equals("Top Rated")){
            loadMoviesList("top_rated");
        }else
            if (clickedButton.equals("Popular")){
                loadMoviesList("popular");
            }
        return super.onOptionsItemSelected(item);
    }

    public void loadMoviesList(String path){

        AsyncTask asyncTask = new AsyncTask();

        GridLayoutManager layoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);

        mMoviesList.setLayoutManager(layoutManager);



        if (connected(this)){
            asyncTask.execute(path);
        }else{
            Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show();
        }

    }

    public class AsyncTask extends android.os.AsyncTask<String,Void,ArrayList<Movie>> {

        ArrayList<Movie> movies = new ArrayList<>();


        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {

            String path = strings[0];
            try {
                searchUrl = movieApiUtils.createURL(path);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            movies = movieApiUtils.getMovies(searchUrl);

            return movies;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {

            movieResponse = movies;

            mMoviesList.setAdapter(null);

            mMoviesAdapter = new MoviesAdapter(getApplicationContext());

            mMoviesAdapter.setmMovies(movies);

            mMoviesList.setAdapter(mMoviesAdapter);

            mMoviesAdapter.notifyDataSetChanged();

            mLoading.setVisibility(View.INVISIBLE);

            super.onPostExecute(movies);
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

}

