package com.example.android.popular_movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.popular_movies.models.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    private List<Review> mDataset;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;

        MyViewHolder(LinearLayout v) {
            super(v);
            mLinearLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    ReviewAdapter(List<Review> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Review review = mDataset.get(position);
        TextView authorTv = holder.mLinearLayout.findViewById(R.id.author);
        authorTv.setText(review.author);

        TextView contentTv = holder.mLinearLayout.findViewById(R.id.content);
        contentTv.setText(review.content);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
