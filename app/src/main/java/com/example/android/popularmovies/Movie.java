package com.example.android.popularmovies;

/**
 * Movie object holds all relevant information for a single movie.
 */

public class Movie {

    private String mPoster;
    private String mTitle;
    private String mReleaseDate;
    private String mOverview;
    private double mVoteAverage;
    private int mId;

    public Movie(String poster, String title, String releaseDate, String overview, double voteAverage, int id) {
        mPoster = poster;
        mTitle = title;
        mReleaseDate = releaseDate;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mId = id;
    }

    public String getPoster() {
        return mPoster;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getOverview() {
        return mOverview;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public int getId() {
        return mId;
    }
}
