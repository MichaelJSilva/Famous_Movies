package com.prototype48.michael.famous_movies.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.prototype48.michael.famous_movies.R;
import com.prototype48.michael.famous_movies.database.MovieContentProvider;
import com.prototype48.michael.famous_movies.database.MovieContract;
import com.prototype48.michael.famous_movies.database.MovieDBHelper;

public class MovieDatabaseUtils {

    private SQLiteDatabase database;

    private Context mContext;

    private MovieDBHelper movieDBHelper;

    public MovieDatabaseUtils(Context context){
        mContext = context;
        movieDBHelper = new MovieDBHelper(mContext);
    }


    public void addFavoriteMovie(long id,String name,String description,String posterPath){


        ContentValues row = new ContentValues();

        row.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,id);

        row.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME,name);

        row.put(MovieContract.MovieEntry.COLUMN_MOVIE_DESCRIPTION,description);

        row.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTERPATH,posterPath);

        Uri uri = mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,row);

        Toast.makeText(mContext, R.string.added_to_favorites,Toast.LENGTH_SHORT).show();


    }

    public void removeFavoriteMovie(long movieId){

        Uri uri =  MovieContract.BASE_CONTENT_URI;

        uri = uri.buildUpon().appendPath(MovieContract.PATH_MOVIES)
                             .appendPath(String.valueOf(movieId)).build();

        Bundle values = new Bundle();

        values.putLong(MovieContract.MovieEntry.COLUMN_MOVIE_ID,movieId);

        int deleted = mContext.getContentResolver().delete(uri,null,null);

        Toast.makeText(mContext, R.string.removed_from_favorites,Toast.LENGTH_SHORT).show();


    }


    public int findFavoriteByID(long movieId){

        Uri uri =  MovieContract.BASE_CONTENT_URI;

        uri = uri.buildUpon().appendPath(MovieContract.PATH_MOVIES)
                             .appendPath(String.valueOf(movieId)).build();

        Log.d(uri.toString(),uri.toString());

        Bundle values = new Bundle();

        values.putLong(MovieContract.MovieEntry.COLUMN_MOVIE_ID,movieId);


        Cursor movieCursor = mContext.getContentResolver().query(uri,null,values,null);


        if (movieCursor != null) {
            return movieCursor.getCount();
        }else{
            return -1;
        }
    }

    public Cursor findAll(){

        Uri uri =  MovieContract.BASE_CONTENT_URI;

        uri = uri.buildUpon().appendPath(MovieContract.PATH_MOVIES).build();


        Cursor movieCursor = mContext.getContentResolver().query(uri,null,null,null);

     return movieCursor;
    }
}
