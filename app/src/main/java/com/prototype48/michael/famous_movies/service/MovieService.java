package com.prototype48.michael.famous_movies.service;


import android.content.Context;

import com.prototype48.michael.famous_movies.interfaces.AsyncTaskDelegate;
import com.prototype48.michael.famous_movies.utils.MovieApiUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieService extends android.os.AsyncTask<String,Void,ArrayList<?>>  {

    private static MovieService movieServiceInstance;

    private AsyncTaskDelegate delegate = null;

    private Context mContext;

    private long mMovieId;

    private final static String PATH_TOP_RATED = "top_rated";

    private final static String PATH_POPULAR   = "popular";


    public MovieService(Context context, AsyncTaskDelegate responder,long movieID){
        this.delegate = responder;
        this.mContext = context;
        this.mMovieId = movieID;
    }


    public MovieService getInstance(Context context, AsyncTaskDelegate responder,long movieID){
        if (movieServiceInstance == null){
            movieServiceInstance = new MovieService(mContext,delegate,mMovieId);
        }
        return movieServiceInstance;
    }


    private MovieApiUtils movieApiUtils = new MovieApiUtils();

    private URL searchUrl = null;

    ArrayList<?> data = new ArrayList<>();

    @Override
    protected ArrayList<?> doInBackground(String... strings) {

        String path = strings[0];
        try {
            switch (path){
                case "videos":
                    searchUrl = movieApiUtils.createTrailerURL(mMovieId);
                    data = movieApiUtils.getMovieTrailers(searchUrl);
                    break;
                case "reviews":
                    searchUrl = movieApiUtils.createReviewsURL(mMovieId);
                    data = movieApiUtils.getMovieReviews(searchUrl);
                    break;
                case "Top Rated":
                    searchUrl = movieApiUtils.createURL(PATH_TOP_RATED);
                    data = movieApiUtils.getMovies(searchUrl);
                    break;
                case "Popular":
                    searchUrl = movieApiUtils.createURL(PATH_POPULAR);
                    data = movieApiUtils.getMovies(searchUrl);
                    break;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<?> data) {

        super.onPostExecute(data);
        if (delegate != null){
            delegate.afterPost(data);
        }
    }


}
