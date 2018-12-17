package com.example.android.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    final static String TMDB_BASE_PATH = "http://image.tmdb.org/t/p/";
    final static String POSTER_SIZE = "w185";

    final static String POSTER_PATH = TMDB_BASE_PATH + POSTER_SIZE;

    private List<JSONObject> mDataset;
    private Context mContext;

    public MyAdapter(List<JSONObject> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public MyViewHolder(ImageView v) {
            super(v);
            mImageView = v;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        try {
            String posterPath = mDataset.get(i).getString("poster_path");
            String imageUrl = TMDB_BASE_PATH + POSTER_SIZE + posterPath;
            ImageView imageView = myViewHolder.mImageView;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // know exactly what i clicked on because i have the position
                    // put extra mdataset.geti and parse json in new activity
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("clickedPoster", mDataset.get(i).toString());
                    mContext.startActivity(intent);
                }
            });
            Picasso.with(mContext).load(imageUrl).into(imageView);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
