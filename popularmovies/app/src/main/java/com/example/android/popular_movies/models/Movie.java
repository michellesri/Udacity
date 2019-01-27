package com.example.android.popular_movies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Movie {

    @PrimaryKey @NonNull
    public String id;

    public String title;
    public String releaseDate;
    public String posterPath;
    public String moviePoster;
    public String voteAverage;
    public String plotSynopsis;

    public Movie(@NonNull String id, String title, String releaseDate, String posterPath, String moviePoster, String voteAverage, String plotSynopsis) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.moviePoster = moviePoster;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }
}
