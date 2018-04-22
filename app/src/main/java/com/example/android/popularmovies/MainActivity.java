package com.example.android.popularmovies;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    String movieUrl = "";
    String posterPath = "";
    String imageSize = "w185/";
    MovieAdapter mAdapter;
    Boolean isConnected;
    List<Movie> mMovies;
    TextView mEmptyTextView;
    ProgressBar mLoadingIndicator;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mEmptyTextView = findViewById(R.id.empty_text_view);
        mLoadingIndicator = findViewById(R.id.loading_indicator);

        mEmptyTextView.setVisibility(View.GONE);
        mLoadingIndicator.setVisibility(View.VISIBLE);

        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MovieAdapter(this, new ArrayList<Movie>(), this);
        recyclerView.setAdapter(mAdapter);

        movieUrl = TMDB_REQUEST_URL + POPULAR + API_KEY;
        //movieUrl = TMDB_REQUEST_URL + TOP_RATED + API_KEY;

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(MOVIE_LOADER_ID, null, MainActivity.this);


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

        Bundle movie = new Bundle();
        movie.putString("Poster", posterPath);
        movie.putString("Title", movieTitle);
        movie.putString("Date", releaseDate);
        movie.putString("Vote Average", voteAverage);
        movie.putString("Overview", overview);

        Intent startDetailActivityIntent = new Intent(MainActivity.this, DetailActivity.class);
        startDetailActivityIntent.putExtra("Clicked movie", movie);

        startActivity(startDetailActivityIntent);


    }

    //public boolean checkConnectivity() {
    // ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    //NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    //isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    //return isConnected;
    //}
    //TODO 2. Patobulinti UI, pridėti loadinimo indikatorių.
    //TODO 3. Pagalvoti dėl landscape mode
    //TODO 4. Strings, styles and other.
    //TODO 5. onSaveInstance Negrįžta į aktivity
    //TODO 6. connectivity check


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_popular_movies:
                movieUrl = TMDB_REQUEST_URL + POPULAR + API_KEY;
                getLoaderManager().restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);
                return true;
            case R.id.action_top_rated_movies:
                movieUrl = TMDB_REQUEST_URL + TOP_RATED + API_KEY;
                getLoaderManager().restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
