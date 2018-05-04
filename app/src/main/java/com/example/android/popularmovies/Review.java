package com.example.android.popularmovies;

public class Review {

    private String mReviewAuthor;
    private String mReviewUrl;

    public Review(String reviewAuthor, String reviewUrl) {
        mReviewAuthor = reviewAuthor;
        mReviewUrl = reviewUrl;
    }

    public String getReviewAuthor() {
        return mReviewAuthor;
    }

    public String getReviewUrl() {
        return mReviewUrl;
    }
}
