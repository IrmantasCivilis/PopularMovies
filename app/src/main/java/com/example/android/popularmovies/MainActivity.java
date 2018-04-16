package com.example.android.popularmovies;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>{
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int MOVIE_LOADER_ID = 1;

    private static final String API_KEY = "";
    private static final String TMDB_REQUEST_URL = "https://api.themoviedb.org/3/movie/";
    private static final String POPULAR = "popular?";
    private static final String TOP_RATED = "top_rated?";

    String movieUrl = "";

    String posterPath = "";
    String imageSize = "w185/";
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";

    private static final int NUM_LIST_ITEMS = 100;

    MovieAdapter mAdapter;
    Boolean isConnected;
    List<Movie> mMovies;
    TextView textView;
    ImageView imageView;

    //private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MovieAdapter(this, new ArrayList<Movie>());
        recyclerView.setAdapter(mAdapter);

        movieUrl = TMDB_REQUEST_URL + POPULAR + API_KEY;

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(MOVIE_LOADER_ID, null, MainActivity.this);


    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        return new MovieLoader(this, movieUrl);
    }


    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {

        if (movies != null && !movies.isEmpty()) {
            mAdapter.setMovieData(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    //public boolean checkConnectivity() {
       // ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        //isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        //return isConnected;
    //}
    //TODO 1. Pridėti meniu su pasirinkimu top rated paieška
    //TODO 2. SUkurti detail activity ir layoutą
    //TODO 3.  Sujungti activities intentu
    //TODO 4. onclick listenerį uždėti ant posterių
    //TODO 5. Patobulinti UI, pridėti loadinimo indikatorių.
    //TODO 6. Pagalvoti dėl landscape mode
    //TODO 7.

}
