package com.example.android.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.data.FavoriteContract.FavoriteEntry;
import com.example.android.popularmovies.data.FavoriteDbHelper;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Movie>>,
        MovieAdapter.GridItemClickListener {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int MOVIE_LOADER_ID = 1;
    private static final String API_KEY = "";
    private static final String TMDB_REQUEST_URL = "https://api.themoviedb.org/3/movie/";
    private static final String POPULAR = "popular?";
    private static final String TOP_RATED = "top_rated?";

    String movieUrl = "";
    MovieAdapter mAdapter;
    Boolean isConnected;
    TextView mEmptyTextView;
    ProgressBar mLoadingIndicator;

    private SQLiteDatabase mDb;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mEmptyTextView = findViewById(R.id.empty_text_view);
        mLoadingIndicator = findViewById(R.id.loading_indicator);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MovieAdapter(this, new ArrayList<Movie>(), this);
        recyclerView.setAdapter(mAdapter);

        showLoadingIndicator();
        movieUrl = TMDB_REQUEST_URL + POPULAR + API_KEY;

        if (checkConnectivity()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(MOVIE_LOADER_ID, null, MainActivity.this);
        } else {
            showNoConnection();
        }

        FavoriteDbHelper dbHelper = new FavoriteDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        mCursor = getAllFavoriteMovies();

    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        return new MovieLoader(this, movieUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {

        mLoadingIndicator.setVisibility(View.GONE);

        mAdapter.clearAdapter();

        if (movies != null && !movies.isEmpty()) {
            mAdapter.setMovieData(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.clearAdapter();
    }

    @Override
    public void onGridItemClick(Movie clickedMovie) {

        String posterPath = clickedMovie.getPoster();
        String movieTitle = clickedMovie.getTitle();
        String releaseDate = clickedMovie.getReleaseDate();
        String voteAverage = String.valueOf(clickedMovie.getVoteAverage());
        String overview = clickedMovie.getOverview();
        String movieId = String.valueOf(clickedMovie.getId());

        Bundle movie = new Bundle();
        movie.putString("Poster", posterPath);
        movie.putString("Title", movieTitle);
        movie.putString("Date", releaseDate);
        movie.putString("Vote Average", voteAverage);
        movie.putString("Overview", overview);
        movie.putString("Id", movieId);

        Intent startDetailActivityIntent = new Intent(MainActivity.this, DetailActivity.class);
        startDetailActivityIntent.putExtra("Clicked movie", movie);

        startActivity(startDetailActivityIntent);
    }

    public boolean checkConnectivity() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_popular_movies:

                mAdapter.clearAdapter();
                showLoadingIndicator();
                movieUrl = TMDB_REQUEST_URL + POPULAR + API_KEY;
                if (checkConnectivity()) {
                    getLoaderManager().restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);
                } else {
                    showNoConnection();
                }
                return true;

            case R.id.action_top_rated_movies:

                mAdapter.clearAdapter();
                showLoadingIndicator();
                movieUrl = TMDB_REQUEST_URL + TOP_RATED + API_KEY;
                if (checkConnectivity()) {
                    getLoaderManager().restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);
                } else {
                    showNoConnection();
                }
                return true;

            case R.id.action_favorite_movies:

                mAdapter.clearAdapter();
                if (mCursor.getCount() == 0) {
                    mEmptyTextView.setVisibility(View.VISIBLE);
                    mEmptyTextView.setText("Favorite Movies database is empty");
                }

        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoadingIndicator() {
        mEmptyTextView.setVisibility(View.GONE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    public void showNoConnection() {
        mLoadingIndicator.setVisibility(View.GONE);
        mEmptyTextView.setVisibility(View.VISIBLE);
        mEmptyTextView.setText(R.string.no_internet_connection);
    }

    private Cursor getAllFavoriteMovies() {
        return mDb.query(
                FavoriteEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FavoriteEntry._ID
        );
    }
}
