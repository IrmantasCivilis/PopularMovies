package com.example.android.popularmovies.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.popularmovies.QueryUtils;
import com.example.android.popularmovies.customclasses.Trailer;

import java.util.List;

public class TrailerLoader extends AsyncTaskLoader<List<Trailer>> {
    private String mUrl;

    public TrailerLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Trailer> loadInBackground() {

        if (mUrl == null) {
            return null;
        }
        return QueryUtils.fetchVideoData(mUrl);
    }
}
