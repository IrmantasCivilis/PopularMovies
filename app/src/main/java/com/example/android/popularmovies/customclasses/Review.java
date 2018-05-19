package com.example.android.popularmovies.customclasses;

public class Review {

    private String mReviewAuthor;
    private String mReviewContent;

    public Review(String reviewAuthor, String reviewContent) {
        mReviewAuthor = reviewAuthor;
        mReviewContent = reviewContent;
    }

    public String getReviewAuthor() {
        return mReviewAuthor;
    }

    public String getReviewContent() {
        return mReviewContent;
    }
}
