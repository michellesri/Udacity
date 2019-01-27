package com.example.android.popular_movies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Video {

    @PrimaryKey @NonNull
    public String movieId;

    public String youtubeKey;

    public Video(@NonNull String movieId, String youtubeKey) {
        this.movieId = movieId;
        this.youtubeKey = youtubeKey;
    }
}
