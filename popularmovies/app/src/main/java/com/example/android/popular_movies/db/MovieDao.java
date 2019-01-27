package com.example.android.popular_movies.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popular_movies.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie WHERE id = :id")
    LiveData<Movie> getMovieById(String id);

    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Movie> movies);


}
