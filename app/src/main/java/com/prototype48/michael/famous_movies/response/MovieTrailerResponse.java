package com.prototype48.michael.famous_movies.response;

import com.google.gson.annotations.SerializedName;
import com.prototype48.michael.famous_movies.model.MovieTrailer;

import java.util.ArrayList;

public class MovieTrailerResponse {

    @SerializedName("id")
    private long id;
    @SerializedName("results")
    private ArrayList<MovieTrailer> results;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<MovieTrailer> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieTrailer> results) {
        this.results = results;
    }
}
