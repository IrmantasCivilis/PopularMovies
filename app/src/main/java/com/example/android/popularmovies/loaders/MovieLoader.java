package com.example.android.popularmovies.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.popularmovies.customclasses.Movie;
import com.example.android.popularmovies.QueryUtils;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {

        if (mUrl == null) {
            return null;
        }
        return QueryUtils.fetchMovieData(mUrl);
    }
}
