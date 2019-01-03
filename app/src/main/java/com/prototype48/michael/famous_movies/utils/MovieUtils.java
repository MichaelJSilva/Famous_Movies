package com.prototype48.michael.famous_movies.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.prototype48.michael.famous_movies.model.Movie;

public class MovieUtils {

    public static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";
    public static final String POSTER_BACKGROUND_SIZE_URL = "w185/";
    public static final String POSTER_SCREEN_SIZE_URL     = "w500/";

    public String createPosterPath(Movie movie, Boolean screenBG){

        String completePath;

        if (screenBG){
            completePath = (MovieUtils.BASE_POSTER_URL + MovieUtils.POSTER_BACKGROUND_SIZE_URL + movie.getposterPath());
        }else{
            completePath = (MovieUtils.BASE_POSTER_URL + MovieUtils.POSTER_SCREEN_SIZE_URL + movie.getposterPath());
        }

        return completePath;
    }

    public void loadPosterImageView(Context context, ImageView imageView, String posterPath,Boolean backgroundImage){

        if (backgroundImage){
            Glide.with(context)
                    .load(posterPath)
                    .apply(new RequestOptions().centerCrop())
                    .into(imageView);
        }else {
            Glide.with(context)
                    .load(posterPath)
                    .apply(new RequestOptions().fitCenter())
                    .into(imageView);
        }

    }


}
