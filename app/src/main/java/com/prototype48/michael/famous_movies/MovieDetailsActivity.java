package com.prototype48.michael.famous_movies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.prototype48.michael.famous_movies.adapter.MovieDetailsPageAdapter;
import com.prototype48.michael.famous_movies.adapter.MoviesAdapter;
import com.prototype48.michael.famous_movies.model.Movie;
import com.prototype48.michael.famous_movies.utils.MovieDatabaseUtils;


public class MovieDetailsActivity extends FragmentActivity implements View.OnClickListener {

    public static final String TAG_MOVIE = "movie";
    public static final String TAG_INTENT = "intent";
    public static final String TAG_POSTER = "poster";
    public static final String TAG_BG = "background";
    private static final int MOVIE_LOADER_ID = 0;

    private ViewPager mViewPager;
    private FloatingActionButton mbtnFavorite;
    MovieDatabaseUtils movieDatabaseUtils = new MovieDatabaseUtils(this);
    String  imageURL;
    String  bgURL;
    Movie   mMovie;
    Intent  intent;
    Boolean mFavorite;



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TAG_POSTER,imageURL);
        outState.putString(TAG_BG,bgURL);
        outState.putParcelable(TAG_INTENT,intent);
        outState.putParcelable(TAG_MOVIE,mMovie);
    }


    //    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_movie_details);

        mViewPager = (ViewPager) findViewById(R.id.vpMovieDetails);
        mbtnFavorite = (FloatingActionButton) findViewById(R.id.btnFavorite);

        // get data from saved instance state if there`s already data
        if (savedInstanceState != null){
            mMovie    = savedInstanceState.getParcelable(TAG_MOVIE);
            imageURL = savedInstanceState.getString(TAG_POSTER);
            bgURL    = savedInstanceState.getString(TAG_BG);
            intent   = savedInstanceState.getParcelable(TAG_INTENT);

        }else{
            // recover data from intent
            intent = getIntent();
            mMovie    = (Movie) getIntent().getExtras().getParcelable(TAG_MOVIE);
            imageURL = (String) intent.getSerializableExtra(TAG_POSTER);
            bgURL    = (String) intent.getSerializableExtra(TAG_BG);
        }

        // verify if the selected movie is a  favorite movie
        if (mMovie != null) {
            mFavorite = isFavorite(mMovie);
        }


        MovieDetailsPageAdapter movieDetailsPageAdapter = new MovieDetailsPageAdapter(getSupportFragmentManager(),mMovie);
        mViewPager.setAdapter(movieDetailsPageAdapter);

        mbtnFavorite.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        int viewID = v.getId();

        movieDatabaseUtils = new MovieDatabaseUtils(getBaseContext());

        Movie movieClicked;

        movieClicked = mMovie;

        // make sure the item is in favorites
        mFavorite = isFavorite(movieClicked);

        if (!mFavorite) {
            movieDatabaseUtils.addFavoriteMovie(movieClicked.getId(), movieClicked.getTitle(), movieClicked.getOverview(),movieClicked.getposterPath());
        }else{
            movieDatabaseUtils.removeFavoriteMovie(movieClicked.getId());
        }

        // verify item added to favorites, to change icons
        isFavorite(movieClicked);


    }

    public Boolean isFavorite(Movie movie){

        int favoriteID;
        // verify if the selected movie is a favorite in database
        favoriteID = movieDatabaseUtils.findFavoriteByID(movie.getId());

        if (favoriteID > 0){

            mbtnFavorite.setImageResource(R.drawable.baseline_favorite_black_24dp);

            return true;
        }else{

            mbtnFavorite.setImageResource(R.drawable.baseline_favorite_border_black_24dp);

            return false;
        }

    }
}
