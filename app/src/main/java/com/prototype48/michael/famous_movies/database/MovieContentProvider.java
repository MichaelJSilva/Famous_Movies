package com.prototype48.michael.famous_movies.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

public class MovieContentProvider extends ContentProvider {

    private MovieDBHelper movieDBHelper;
    private Context context;
    private SQLiteDatabase database;

    public static final int MOVIES = 100;
    public static final int MOVIES_ID = 101;

    private static UriMatcher sUriMatcher = buildUriMatcher();


    @Override
    public boolean onCreate() {

        context = getContext();

        movieDBHelper = new MovieDBHelper(context);

        return false;
    }

    public static UriMatcher buildUriMatcher(){

        UriMatcher uriMatcher =  new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.AUTHORITY,MovieContract.PATH_MOVIES,MOVIES);

        uriMatcher.addURI(MovieContract.AUTHORITY,MovieContract.PATH_MOVIES + "/#",MOVIES_ID);

        return uriMatcher;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        database = movieDBHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor returnCursor = null;


        switch (match){
            case MOVIES:
                returnCursor = database.query(MovieContract.TABLE_NAME,
                                              projection,
                                              selection,
                                              selectionArgs,
                                      null,
                                       null,
                                              sortOrder );

                break;
            case MOVIES_ID:

                String id = uri.getPathSegments().get(1);

                String mSelection = "id=?";

                String[] mSelectionArgs = new String[]{id};

                returnCursor = database.query(MovieContract.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder );


                break;
        }

        if (returnCursor != null) {
            returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        database = movieDBHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);


        Uri returnUri;

        switch (match){
            case MOVIES:
                long id = database.insert(MovieContract.TABLE_NAME,null,values);
                if (id > 0){
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI,id);
                }
                break;
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        database = movieDBHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int movieDeleted = 0;



        switch (match) {
            case MOVIES_ID:

                String id = uri.getPathSegments().get(1);

                String[] mSelectionArgs = new String[]{id};

                movieDeleted = database.delete(MovieContract.TABLE_NAME, "id=?", mSelectionArgs);

                break;
        }

        getContext().getContentResolver().notifyChange(uri,null);


        return movieDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
