package com.prototype48.michael.famous_movies.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    public static final String TABLE_NAME = "Movie";

    public static final String AUTHORITY = "com.prototype48.michael.famous_movies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static  final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String COLUMN_MOVIE_MOVIEDBKEY = "MovieDbKey";
        public static final String COLUMN_MOVIE_ID = "Id";
        public static final String COLUMN_MOVIE_NAME = "Name";
        public static final String COLUMN_MOVIE_DESCRIPTION = "Description";
        public static final String COLUMN_MOVIE_POSTERPATH = "PosterPath";


    }


}
