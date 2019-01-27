package com.example.android.popular_movies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.android.popular_movies.db.MyRoomDatabase;
import com.example.android.popular_movies.models.Review;

import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String movieId = getIntent().getStringExtra("movieId");

        LiveData<List<Review>> reviews = MyRoomDatabase.getInstance(this).reviewDao().getAllReviewsForMovie(movieId);
        reviews.observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                renderReviews(reviews);
            }
        });
    }

    public void renderReviews(List<Review> reviews) {
        // specify an adapter (see also next example)
        TextView numReviewTv = findViewById(R.id.num_review_tv);
        String numReviewsString = getString(R.string.num_reviews, reviews.size());
        numReviewTv.setText(numReviewsString);
        RecyclerView.Adapter mAdapter = new ReviewAdapter(reviews);
        mRecyclerView.setAdapter(mAdapter);

    }
}
