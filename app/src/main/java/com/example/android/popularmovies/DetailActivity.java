package com.example.android.popularmovies;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//public class DetailActivity extends AppCompatActivity
//        implements LoaderManager.LoaderCallbacks<List<Trailer>>,
//        TrailerAdapter.TrailerListItemClickListener,
//        ReviewAdapter.ReviewListItemClickListener {

    public class DetailActivity extends AppCompatActivity
            implements LoaderManager.LoaderCallbacks,
            TrailerAdapter.TrailerListItemClickListener,
            ReviewAdapter.ReviewListItemClickListener {

    private static final String IMAGE_SIZE = "w185/";
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";

    private static final String API_KEY = "";
    private static final String TMDB_REQUEST_URL = "https://api.themoviedb.org/3/movie/";
    private static final String VIDEOS = "/videos?";
    private static final String TRAILER_REQUEST_URL = "https://www.youtube.com/watch?v=";
    private static final String REVIEWS = "/reviews?";

    private static final int TRAILER_LOADER_ID = 2;
    private static final int REVIEW_LOADER_ID = 3;
    String videosUrl = "";
    String trailerUrl = "";
    String reviewsUrl = "";
    TrailerAdapter mTrailerAdapter;
    ReviewAdapter mReviewAdapter;

    Boolean isOverviewClicked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView mMoviePoster = findViewById(R.id.poster_image_view);
        TextView mTitle = findViewById(R.id.title_text_view);
        TextView mReleaseDate = findViewById(R.id.release_date_text_view);
        TextView mVoteAverage = findViewById(R.id.average_vote_text_view);
        final TextView mOverview = findViewById(R.id.overview_text_view);
        //TextView mMovieId = findViewById(R.id.movie_id);
        mOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOverviewClicked) {
                    mOverview.setMaxLines(3);
                    isOverviewClicked = false;
                } else {
                    mOverview.setMaxLines(Integer.MAX_VALUE);
                    isOverviewClicked = true;
                }
            }
        });

        Intent intentThatStartedThisActivity = getIntent();

        Bundle movie = intentThatStartedThisActivity.getBundleExtra("Clicked movie");

        if (intentThatStartedThisActivity.hasExtra("Clicked movie")){

            String posterPath = movie.getString("Poster");
            Picasso.with(this)
                    .load(BASE_IMAGE_URL + IMAGE_SIZE + posterPath)
                    .into(mMoviePoster);

            String movieTitle = movie.getString("Title");
            mTitle.setText(movieTitle);

            String releaseDate = movie.getString("Date");
            mReleaseDate.setText(releaseDate);

            String voteAverage = movie.getString("Vote Average");
            mVoteAverage.setText(voteAverage);

            String overview = movie.getString("Overview");
            mOverview.setText(overview);

            String id = movie.getString("Id");
            //mMovieId.setText(id);

            RecyclerView trailerRecyclerView = findViewById(R.id.recycler_view_for_trailers);
            RecyclerView.LayoutManager mTrailerLayoutManager = new LinearLayoutManager(this);
            trailerRecyclerView.setLayoutManager(mTrailerLayoutManager);
            trailerRecyclerView.setHasFixedSize(true);
            mTrailerAdapter = new TrailerAdapter(this, new ArrayList<Trailer>(), this);
            trailerRecyclerView.setAdapter(mTrailerAdapter);

            RecyclerView reviewRecyclerView = findViewById(R.id.recycler_view_for_reviews);
            RecyclerView.LayoutManager mReviewLayoutManager = new LinearLayoutManager(this);
            reviewRecyclerView.setLayoutManager(mReviewLayoutManager);
            reviewRecyclerView.setHasFixedSize(true);
            mReviewAdapter = new ReviewAdapter(this, new ArrayList<Review>(), this);
            reviewRecyclerView.setAdapter(mReviewAdapter);

            videosUrl = TMDB_REQUEST_URL + id + VIDEOS + API_KEY;
            reviewsUrl = TMDB_REQUEST_URL + id + REVIEWS + API_KEY;

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(TRAILER_LOADER_ID, null, DetailActivity.this);
            loaderManager.initLoader(REVIEW_LOADER_ID, null,DetailActivity.this);

        }
    }

    //@Override
    //public Loader<List<Trailer>> onCreateLoader(int i, Bundle bundle) {
    //    return new TrailerLoader(this, videosUrl);
    //}

    //@Override
    //public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> trailers) {

       // mTrailerAdapter.clearTrailerAdapter();

        //if (trailers != null && !trailers.isEmpty()) {
        //    mTrailerAdapter.setTrailersData(trailers);
        //}

    //}

    //@Override
    //public void onLoaderReset(Loader<List<Trailer>> loader) {

     //   mTrailerAdapter.clearTrailerAdapter();
     //   }

    @Override
    public void onTrailerListItemClick(Trailer clickedTrailer) {

        String key = clickedTrailer.getTrailerKey();

        trailerUrl = TRAILER_REQUEST_URL + key;

        Uri trailer = Uri.parse(trailerUrl);

        Intent videoIntent = new Intent(Intent.ACTION_VIEW, trailer);

        if (videoIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(videoIntent);
        }
    }


    @Override
    public void onReviewListItemClick(Review clickedReview) {

        String reviewUrl = clickedReview.getReviewUrl();

        Uri review = Uri.parse(reviewUrl);

        Intent reviewIntent = new Intent(Intent.ACTION_VIEW, review);

        if (reviewIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(reviewIntent);
        }
    }

        @Override
        public Loader onCreateLoader(int i, Bundle bundle) {
           if (i == TRAILER_LOADER_ID) {
               return new TrailerLoader(this, videosUrl);
           } else if(i == REVIEW_LOADER_ID) {
               return new ReviewLoader(this, reviewsUrl);
           }
           return null;
        }

        @Override
        public void onLoadFinished(Loader loader, Object o) {

            int loaderId = loader.getId();
            if (loaderId == TRAILER_LOADER_ID) {
                mTrailerAdapter.clearTrailerAdapter();

                if ((List<Trailer>) o != null) {
                    mTrailerAdapter.setTrailersData((List<Trailer>) o);
                }

            } else if (loaderId == REVIEW_LOADER_ID) {
                mReviewAdapter.clearReviewAdapter();
                if ((List<Review>) o != null) {
                    mReviewAdapter.setReviewsData((List<Review>) o);
                }
            }
        }

        @Override
        public void onLoaderReset(Loader loader) {

        int loaderId = loader.getId();
        if (loaderId == TRAILER_LOADER_ID) {
            mTrailerAdapter.clearTrailerAdapter();
        } else if (loaderId == REVIEW_LOADER_ID) {
            mReviewAdapter.clearReviewAdapter();
        }
        }
    }
