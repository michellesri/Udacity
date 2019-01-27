package com.example.android.popular_movies.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.example.android.popular_movies.models.Video;

@Dao
public interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Video video);
}
