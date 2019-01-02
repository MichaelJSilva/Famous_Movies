package com.prototype48.michael.famous_movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prototype48.michael.famous_movies.R;
import com.prototype48.michael.famous_movies.model.MovieReview;
import com.prototype48.michael.famous_movies.model.MovieTrailer;

import java.util.ArrayList;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewViewHolder> {

    Context mContext;
    ArrayList<MovieReview> mMoviesReviews;
    //views
    TextView mTextAuthor;
    TextView mTextContent;

    public ArrayList<MovieReview> getmMoviesReviews() {
        return mMoviesReviews;
    }

    public void setmMoviesReviews(ArrayList<MovieReview> mMoviesReviews) {
        this.mMoviesReviews = mMoviesReviews;
    }

    public MovieReviewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MovieReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.fragment_reviews_card,viewGroup,false);

        mTextContent = v.findViewById(R.id.tvReviewText);
        mTextAuthor = v.findViewById(R.id.tvAuthorName);

        return new MovieReviewAdapter.MovieReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewViewHolder movieReviewViewHolder, int position) {

        MovieReview movieReview = mMoviesReviews.get(position);

        mTextContent.setText(movieReview.getContent());
        mTextAuthor.setText(movieReview.getAuthor());


    }

    @Override
    public int getItemCount() {
        return mMoviesReviews.size();
    }

    public class MovieReviewViewHolder extends RecyclerView.ViewHolder{


        public MovieReviewViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
