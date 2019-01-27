package com.example.android.popular_movies.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popular_movies.models.Review;

import java.util.List;

@Dao
public interface ReviewDao {

    @Query("SELECT * FROM Review WHERE movieId = :movieId")
    LiveData<List<Review>> getAllReviewsForMovie(String movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Review> reviews);

}
