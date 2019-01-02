package com.prototype48.michael.famous_movies.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.prototype48.michael.famous_movies.R;
import com.prototype48.michael.famous_movies.model.MovieTrailer;

import java.util.ArrayList;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerViewHolder> {

    ArrayList<MovieTrailer> mMovieTrailers;

    public static String trailerURL;


    static TextView mPay;

    private static final String YOUTUBE_THUMB_BASE_URL = "https://img.youtube.com/vi/";

    private static final String YOUTUBE_THUMB_DEFAULT_IMG = "mqdefault.jpg";

    private static final String YOUTUBE_TRAILER_BASE_URL = "https://www.youtube.com/watch?v=";

    private static Context mContext;

    public MovieTrailerAdapter(Context context) {

        this.mContext = context;
    }



    public MovieTrailerAdapter(@NonNull View itemView) {
        super();


    }


    @NonNull
    @Override
    public MovieTrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.fragment_trailer_card,viewGroup,false);


        return new MovieTrailerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerViewHolder movieTrailerViewHolder, int position) {



        MovieTrailer movieTrailer = mMovieTrailers.get(position);

        movieTrailerViewHolder.mTrailerTitle.setText(movieTrailer.getName());

        String trailerKey = movieTrailer.getKey() ;

        Glide.with(movieTrailerViewHolder.mTrailerThumb.getContext())
                .load(YOUTUBE_THUMB_BASE_URL + trailerKey +  "/" + YOUTUBE_THUMB_DEFAULT_IMG)
                .apply(new RequestOptions().centerCrop())
                .into(movieTrailerViewHolder.mTrailerThumb);

        trailerURL = YOUTUBE_TRAILER_BASE_URL + trailerKey;

    }

    @Override
    public int getItemCount() {
        return mMovieTrailers.size();
    }

    public static class MovieTrailerViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        TextView mTrailerTitle;

        ImageView mTrailerThumb;


        public MovieTrailerViewHolder(@NonNull View itemView) {
            super(itemView);

            mTrailerThumb = itemView.findViewById(R.id.trailer_thumb);

            mTrailerTitle = itemView.findViewById(R.id.tvTrailerTitle);

            mTrailerThumb.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            openTrailerPage(trailerURL);
        }
    }

    public static void openTrailerPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        mContext.startActivity(intent);

    }


    public ArrayList<MovieTrailer> getmMovieTrailers() {
        return mMovieTrailers;
    }

    public void setmMovieTrailers(ArrayList<MovieTrailer> mMovieTrailers) {
        this.mMovieTrailers = mMovieTrailers;
    }
}
