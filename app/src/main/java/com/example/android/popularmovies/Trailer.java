package com.example.android.popularmovies;

public class Trailer {

    private String mTrailerName;
    private String mTrailerKey;

    public Trailer(String trailerName, String trailerKey) {
        mTrailerName = trailerName;
        mTrailerKey = trailerKey;
    }

    public String getTrailerName() {
        return mTrailerName;
    }

    public String getTrailerKey() {
        return mTrailerKey;
    }
}
