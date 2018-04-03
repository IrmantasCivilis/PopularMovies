package com.example.android.popularmovies;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>{

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int MOVIE_LOADER_ID = 1;

    private static final String API_KEY = "";
    private static final String TMDB_REQUEST_URL = "http://api.themoviedb.org/3/movie/";
    private static final String POPULAR = "popular?";
    private static final String TOP_RATED = "top_rated?";

    String movieUrl = "";

    String posterPath = "";
    String imageSize = "w185";
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";

    MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.gridview);

        mAdapter = new MovieAdapter(this, new ArrayList<Movie>());
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT)
                        .show();
            }
        });

        movieUrl = TMDB_REQUEST_URL + POPULAR + API_KEY;

        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        return new MovieLoader(this, movieUrl);
    }


    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        mAdapter.clear();
        mAdapter.addAll(movies);

    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.clear();
    }
}
