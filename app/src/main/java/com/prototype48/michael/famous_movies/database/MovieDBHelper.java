package com.prototype48.michael.famous_movies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.prototype48.michael.famous_movies.database.MovieContract;

public class MovieDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "movie.db";

    private static final int DB_VERSION = 3;


    public MovieDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder createDB = new StringBuilder();

        createDB.append(String.format("CREATE TABLE %s ",MovieContract.TABLE_NAME))
                .append(" ( ")
                .append(String.format(" %s INTEGER PRIMARY KEY AUTOINCREMENT, ",MovieContract.MovieEntry.COLUMN_MOVIE_MOVIEDBKEY))
                .append(String.format(" %s INTEGER , ",MovieContract.MovieEntry.COLUMN_MOVIE_ID))
                .append(String.format(" %s TEXT NOT NULL , ",MovieContract.MovieEntry.COLUMN_MOVIE_NAME))
                .append(String.format(" %s TEXT NULL , ",MovieContract.MovieEntry.COLUMN_MOVIE_DESCRIPTION))
                .append(String.format(" %s TEXT NULL ",MovieContract.MovieEntry.COLUMN_MOVIE_POSTERPATH))
                .append(" ) ");

        db.execSQL(createDB.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.TABLE_NAME);
        onCreate(db);
    }
}
