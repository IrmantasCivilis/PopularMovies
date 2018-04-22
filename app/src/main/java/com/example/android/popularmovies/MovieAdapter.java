package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private List<Movie> mMovies;
    private LayoutInflater mInflater;
    private AdapterView.OnItemClickListener mItemClickListener;
    private Context mContext;
    private String imageSize = "w185/";
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";

    final private  GridItemClickListener mOnClickListener;
    private Toast mToast;

    public interface GridItemClickListener {
        void onGridItemClick(Movie clickedMovie);
    }

    public MovieAdapter(Context context, List<Movie> movies, GridItemClickListener listener) {
        mContext = context;
        mMovies = movies;
        mOnClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {

        Picasso.with(mContext)
                .load(BASE_IMAGE_URL + imageSize + mMovies.get(position).getPoster())
                .into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
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

    public void setMovieData(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public void clearAdapter(){
        //mMovies = movies;
        mMovies.clear();
        notifyDataSetChanged();
    }
}

