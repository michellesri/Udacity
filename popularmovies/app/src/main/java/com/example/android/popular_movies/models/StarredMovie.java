package com.example.android.popular_movies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class StarredMovie {

    @PrimaryKey
    @NonNull
    public String movieId;

    public StarredMovie(@NonNull String movieId) {
        this.movieId = movieId;
    }
}
