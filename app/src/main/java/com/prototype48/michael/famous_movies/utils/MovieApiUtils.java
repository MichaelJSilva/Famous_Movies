package com.prototype48.michael.famous_movies.utils;

import android.net.Uri;

import com.google.gson.Gson;
import com.prototype48.michael.famous_movies.BuildConfig;
import com.prototype48.michael.famous_movies.model.Movie;
import com.prototype48.michael.famous_movies.model.MovieReview;
import com.prototype48.michael.famous_movies.model.MovieTrailer;
import com.prototype48.michael.famous_movies.response.MovieReviewsResponse;
import com.prototype48.michael.famous_movies.response.MovieTrailerResponse;
import com.prototype48.michael.famous_movies.response.MoviesResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieApiUtils {

    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private final String API_KEY = BuildConfig.API_KEY;
    private final String lang    = "en-US";


    private OkHttpClient client = new OkHttpClient();

    public URL createURL(String namePath) throws MalformedURLException {

        if (namePath == ""){
            namePath = "top_rated";
        }

        Uri uri =  Uri.parse(BASE_URL).buildUpon()
                       .appendEncodedPath("movie/")
                       .appendEncodedPath(namePath + "/")
                       .appendQueryParameter("api_key",API_KEY)
                       .appendQueryParameter("language",lang)
                       .build();

        URL url;

        url = new URL(uri.toString());

        return url;
    }

    public URL createTrailerURL(long movieID) throws MalformedURLException {

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath("movie/")
                .appendEncodedPath(String.valueOf(movieID) + "/")
                .appendEncodedPath("videos")
                .appendQueryParameter("api_key",API_KEY)
                .appendQueryParameter("language",lang)
                .build();

        URL url;

        url = new URL(uri.toString());

        return url;
    }

    public URL createReviewsURL(long movieID) throws MalformedURLException {

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath("movie/")
                .appendEncodedPath(String.valueOf(movieID) + "/")
                .appendEncodedPath("reviews")
                .appendQueryParameter("api_key",API_KEY)
                .appendQueryParameter("language",lang)
                .build();

        URL url;

        url = new URL(uri.toString());

        return url;
    }


    public ArrayList<Movie> getMovies(URL url){

        Gson gson = new Gson();

        MoviesResponse moviesResponse = new MoviesResponse();

        Request request = new Request.Builder()
                                     .url(url)
                                     .build();

        try {
            Response response = client.newCall(request).execute();

            String responseBody = response.body().string();

            if (responseBody != null)
                moviesResponse = gson.fromJson(responseBody,MoviesResponse.class);
            else
                moviesResponse = null;


        } catch (IOException e) {
            e.printStackTrace();
        }

        if (moviesResponse.getMovies() != null){
            return moviesResponse.getMovies();
        }else {
            return null;
        }
    }

    public ArrayList<MovieTrailer> getMovieTrailers(URL url) throws Exception {

        Gson gson = new Gson();

        MovieTrailerResponse movieTrailerResponse = new MovieTrailerResponse();

        Request request = new Request.Builder()
                .url(url)
                .build();


        try {

            Response response = client.newCall(request).execute();

            String responseBody = response.body().string();

            if (responseBody != null)
                movieTrailerResponse = gson.fromJson(responseBody,MovieTrailerResponse.class);
            else
                movieTrailerResponse = null;


        }catch (Exception e){
            throw new Exception(e.getCause());
        }


        if (movieTrailerResponse.getResults() != null){
            return movieTrailerResponse.getResults();
        }else {
            return null;
        }


    }

    public ArrayList<MovieReview> getMovieReviews(URL url) throws Exception {

        Gson gson = new Gson();

        MovieReviewsResponse movieReviewsResponse = new MovieReviewsResponse();

        Request request = new Request.Builder()
                .url(url)
                .build();


        try {

            Response response = client.newCall(request).execute();

            String responseBody = response.body().string();

            if (responseBody != null)
                movieReviewsResponse = gson.fromJson(responseBody,MovieReviewsResponse.class);
            else
                movieReviewsResponse = null;


        }catch (Exception e){
            throw new Exception(e.getCause());
        }


        if (movieReviewsResponse.getReviews() != null){
            return movieReviewsResponse.getReviews();
        }else {
            return null;
        }


    }




}
