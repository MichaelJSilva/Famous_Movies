package com.prototype48.michael.famous_movies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.prototype48.michael.famous_movies.R;
import com.prototype48.michael.famous_movies.database.MovieContract;

import java.util.zip.Inflater;

public class MovieCursorAdapter extends RecyclerView.Adapter<MovieCursorAdapter.MovieCursorViewHolder> {

    //baseURL
    private final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";
    private final String POSTER_BACKGROUND_SIZE_URL = "w185/";

    Context mContext;

    private Cursor mCursor;

    public MovieCursorAdapter(Context context) {

        this.mContext = context;
    }



    @NonNull
    @Override
    public MovieCursorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.activity_movies_favorites_card,viewGroup,false);

        return new MovieCursorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCursorViewHolder movieCursorViewHolder, int position) {

        int columnIdIndex           = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
        int columnNameIndex         = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_NAME);
        int columnOverviewIndex     = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_DESCRIPTION);
        int columnPosterIndex       = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTERPATH);

        mCursor.moveToPosition(position);

        final int     id             = mCursor.getInt(columnIdIndex);
        final String  movieName      = mCursor.getString(columnNameIndex);
        final String  movieOverview  = mCursor.getString(columnOverviewIndex);
        final String  posterPath     = mCursor.getString(columnPosterIndex);


        movieCursorViewHolder.mMovieTitle.setText(movieName);

        movieCursorViewHolder.mMovieOverview.setText(movieOverview);


        Glide.with(movieCursorViewHolder.mMoviePoster.getContext())
                .load(BASE_POSTER_URL + POSTER_BACKGROUND_SIZE_URL + posterPath)
                .apply(new RequestOptions().centerCrop())
                .into(movieCursorViewHolder.mMoviePoster);


    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class MovieCursorViewHolder extends RecyclerView.ViewHolder{

        ImageView mMoviePoster;

        TextView mMovieTitle;

        TextView mMovieOverview;

        public MovieCursorViewHolder(@NonNull View itemView) {
            super(itemView);

            mMoviePoster    =  itemView.findViewById(R.id.favorite_movie_poster);

            mMovieTitle     = itemView.findViewById(R.id.favorite_movie_title);

            mMovieOverview  = itemView.findViewById(R.id.favorite_movie_overview);
        }

    }


    public Cursor getmCursor() {
        return mCursor;
    }

    public void setmCursor(Cursor mCursor) {
        this.mCursor = mCursor;
    }
}
