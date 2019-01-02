package com.prototype48.michael.famous_movies.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.prototype48.michael.famous_movies.model.MovieReview;

import java.util.ArrayList;

public class MovieReviewsResponse implements Parcelable {

    @SerializedName("id")
    private String mID;
    @SerializedName("page")
    private int mPage;
    @SerializedName("results")
    private ArrayList<MovieReview> mReviews;

    public MovieReviewsResponse(Parcel in) {
        mID = in.readString();
        mPage = in.readInt();

    }


    public static final Creator<MovieReviewsResponse> CREATOR = new Creator<MovieReviewsResponse>() {
        @Override
        public MovieReviewsResponse createFromParcel(Parcel in) {
            return new MovieReviewsResponse(in);
        }

        @Override
        public MovieReviewsResponse[] newArray(int size) {
            return new MovieReviewsResponse[size];
        }
    };

    public MovieReviewsResponse() {

    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public int getmPage() {
        return mPage;
    }

    public void setmPage(int mPage) {
        this.mPage = mPage;
    }

    public ArrayList<MovieReview> getReviews() {
        return mReviews;
    }

    public void setReviews(ArrayList<MovieReview> reviews) {
        this.mReviews = reviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeInt(mPage);
    }



}
