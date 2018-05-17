package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.customclasses.Movie;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Picasso library is used to handle image loading and caching.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private static final String IMAGE_SIZE = "w185/";
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    final private GridItemClickListener mOnClickListener;
    private List<Movie> mMovies;
    private Context mContext;

    public MovieAdapter(Context context, List<Movie> movies, GridItemClickListener listener) {
        mContext = context;
        mMovies = movies;
        mOnClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {

        Picasso.with(mContext)
                .load(BASE_IMAGE_URL + IMAGE_SIZE + mMovies.get(position).getPoster())
                .into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setMovieData(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        mMovies.clear();
        notifyDataSetChanged();
    }

    public interface GridItemClickListener {
        void onGridItemClick(Movie clickedMovie);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView posterImageView;

        private ViewHolder(View itemView) {
            super(itemView);

            posterImageView = itemView.findViewById(R.id.poster_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Movie clickedMovie = mMovies.get(clickedPosition);
            mOnClickListener.onGridItemClick(clickedMovie);
        }
    }
}

