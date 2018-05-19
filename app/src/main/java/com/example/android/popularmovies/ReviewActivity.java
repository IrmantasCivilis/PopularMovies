package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        TextView reviewTextView = findViewById(R.id.review);

        Intent intentThatStartedThisActivity = getIntent();
        Bundle review = intentThatStartedThisActivity.getBundleExtra("Chosen review");

        if (intentThatStartedThisActivity.hasExtra("Chosen review")){

            String reviewAuthor = review.getString("Author");
            setTitle("Review by" + reviewAuthor);

            String reviewContent = review.getString("Content");
            reviewTextView.setText(reviewContent);
        }
    }
}
