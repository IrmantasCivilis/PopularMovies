package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.customclasses.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context mContext;
    private List<Review> mReviews;
    final private ReviewListItemClickListener mOnClickListener;

    public ReviewAdapter(Context context, List<Review> review, ReviewListItemClickListener onClickListener) {
        mContext = context;
        mReviews = review;
        mOnClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.recyclerview_review_item, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        String reviewAuthor = mReviews.get(position).getReviewAuthor();
        String reviewBy = "Review by " + reviewAuthor;
        holder.reviewTextView.setText(reviewBy);

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public interface ReviewListItemClickListener {
        void onReviewListItemClick(Review clickedReview);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView reviewTextView;

        private ViewHolder (View itemView){
            super(itemView);
            reviewTextView = itemView.findViewById(R.id.review_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Review clickedReview = mReviews.get(clickedPosition);
            mOnClickListener.onReviewListItemClick(clickedReview);
        }
    }

    public void setReviewsData(List<Review> reviews) {
        mReviews = reviews ;
        notifyDataSetChanged();
    }

    public void clearReviewAdapter() {
        mReviews.clear();
        notifyDataSetChanged();
    }
}
