package com.example.android.popular_movies.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popular_movies.models.Movie;
import com.example.android.popular_movies.models.StarredMovie;

import java.util.List;

@Dao
public interface StarredMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStarredMovie(StarredMovie starredMovie);

    // the use case for this is to render movies in DetailActivity
    @Query("SELECT * FROM StarredMovie")
    LiveData<List<StarredMovie>> getAllStarredMovies();

    // get all starred movies for MainActivity
    @Query("SELECT * FROM Movie WHERE id IN (SELECT movieId FROM StarredMovie)")
    List<Movie> getFavoriteMovies();

    @Delete
    void delete(StarredMovie starredMovie);


}
