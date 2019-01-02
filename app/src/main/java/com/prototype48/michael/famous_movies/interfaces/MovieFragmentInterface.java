package com.prototype48.michael.famous_movies.interfaces;


import android.support.v4.app.Fragment;

import com.prototype48.michael.famous_movies.model.Movie;

public interface MovieFragmentInterface {

    Fragment create(Movie movie);

    String getFragmentTitle();



}
