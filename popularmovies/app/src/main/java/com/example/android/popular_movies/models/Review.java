package com.example.android.popular_movies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Review {

    @PrimaryKey @NonNull
    public String reviewId;
    public String movieId;

    public String author;
    public String content;

    public Review(@NonNull String reviewId, String movieId, String author, String content) {
        this.reviewId = reviewId;
        this.movieId = movieId;
        this.author = author;
        this.content = content;
    }
}
