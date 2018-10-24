package com.prototype48.michael.famous_movies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.prototype48.michael.famous_movies.adapter.MoviesAdapter;
import com.prototype48.michael.famous_movies.model.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String TAG_MOVIE = "movie";
    public static final String TAG_POSTER = "poster";
    public static final String TAG_BG = "background";

    private TextView mUserRating;
    private TextView mMovieTitle;
    private TextView mMovieOverview;
    private TextView mLaunchDate;
    private ImageView mPoster;
    private ImageView mImageBackground;
    private Uri uri;
    // glide options
    private RequestOptions optionsPoster = new RequestOptions();
    private RequestOptions optionsBg     = new RequestOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_movie_details);




        mUserRating =  findViewById(R.id.tv_user_rating);
        mMovieTitle =  findViewById(R.id.details_movie_title);
        mMovieOverview =  findViewById(R.id.tv_overview);
        mLaunchDate =  findViewById(R.id.tv_launch_date);
        mPoster     =  findViewById(R.id.imageView);
        mImageBackground  =  findViewById(R.id.img_background);


        Intent intent = getIntent();

        Movie movie = (Movie) intent.getSerializableExtra(TAG_MOVIE);
        String imageURL = (String) intent.getSerializableExtra(TAG_POSTER);
        String bgURL    = (String) intent.getSerializableExtra(TAG_BG);

        if (!imageURL.isEmpty()){
            uri = Uri.parse(imageURL);
        }

        if (movie != null){
            mUserRating.setText(String.valueOf(movie.getVote_average()));
            mMovieTitle.setText(movie.getTitle());
            mMovieOverview.setText(movie.getOverview());
            mLaunchDate.setText(String.valueOf(movie.getRelease_date()));

            optionsPoster.fitCenter();
            optionsPoster.centerInside();

            Glide.with(this)
                    .load(uri.toString())
                    .apply(optionsPoster)
                    .into(this.mPoster);


            optionsBg.centerCrop();


            Glide.with(this)
                    .load(bgURL)
                    .apply(optionsBg)
                    .into(this.mImageBackground);




        }


    }
}
