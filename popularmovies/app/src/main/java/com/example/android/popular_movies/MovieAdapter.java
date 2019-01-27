package com.example.android.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popular_movies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    final static String TMDB_BASE_PATH = "http://image.tmdb.org/t/p/";
    final static String POSTER_SIZE = "w185";

    private List<Movie> mDataset;
    private Context mContext;

    MovieAdapter(List<Movie> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView mImageView;

        MyViewHolder(ImageView v) {
            super(v);
            mImageView = v;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final int position = i;
        String posterPath = mDataset.get(i).posterPath;
        String imageUrl = TMDB_BASE_PATH + POSTER_SIZE + posterPath;
        ImageView imageView = myViewHolder.mImageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // know exactly what i clicked on because i have the position
                // put extra mdataset.geti and parse json in new activity
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("clickedMovieId", mDataset.get(position).id);
                mContext.startActivity(intent);
            }
        });
        Picasso.with(mContext).load(imageUrl).into(imageView);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
