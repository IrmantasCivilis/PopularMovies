package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context mContext = getContext();
    List<Movie> mMovies;
    String posterPath = "";
    String imageSize = "w185";
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";

    public MovieAdapter(@NonNull Context context, @NonNull List<Movie> movies) {
        super(context, 0, movies);
        mMovies = movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imageView;

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            imageView = (ImageView) convertView;
            } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(BASE_IMAGE_URL + imageSize + mMovies.get(position).getPoster())
                .into(imageView);

        return imageView;
    }
}
