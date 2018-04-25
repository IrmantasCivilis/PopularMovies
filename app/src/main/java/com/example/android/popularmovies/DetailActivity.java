package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity{

    private static final String IMAGE_SIZE = "w185/";
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView mMoviePoster = findViewById(R.id.poster_iv);
        TextView mTitle = findViewById(R.id.title_tv);
        TextView mReleaseDate = findViewById(R.id.release_date_tv);
        TextView mVoteAverage = findViewById(R.id.average_vote_tv);
        TextView mOverview = findViewById(R.id.overview_tv);

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
        }
    }
}
