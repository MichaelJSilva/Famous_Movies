package com.prototype48.michael.famous_movies.utils;

import android.net.Uri;

import com.google.gson.Gson;
import com.prototype48.michael.famous_movies.model.Movie;
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
    private final String API_KEY = "";
    private final String lang    = "en-US";

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


    public ArrayList<Movie> getMovies(URL url){

        Gson gson = new Gson();

        MoviesResponse moviesResponse = new MoviesResponse();

        OkHttpClient client = new OkHttpClient();

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
}
