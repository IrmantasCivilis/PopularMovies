package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.customclasses.Trailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private Context mContext;
    private List<Trailer> mTrailers;
    final private TrailerListItemClickListener mOnClickListener;

    public TrailerAdapter(Context context, List<Trailer> trailers, TrailerListItemClickListener onClickListener) {
        mContext = context;
        mTrailers = trailers;
        mOnClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.recyclerview_trailer_item, parent, false);
        return new TrailerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String trailerName = mTrailers.get(position).getTrailerName();
        holder.trailerTextView.setText(trailerName);
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public interface TrailerListItemClickListener {
        void onTrailerListItemClick(Trailer clickedTrailer);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView trailerTextView;

        private ViewHolder (View itemView){
            super(itemView);
            trailerTextView = itemView.findViewById(R.id.trailer_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Trailer clickedTrailer = mTrailers.get(clickedPosition);
            mOnClickListener.onTrailerListItemClick(clickedTrailer);
        }
    }

    public void setTrailersData(List<Trailer> trailers) {
        mTrailers = trailers ;
        notifyDataSetChanged();
    }

    public void clearTrailerAdapter() {
        mTrailers.clear();
        notifyDataSetChanged();
    }
}
