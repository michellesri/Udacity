package com.example.android.popular_movies.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.popular_movies.models.Movie;
import com.example.android.popular_movies.models.Review;
import com.example.android.popular_movies.models.StarredMovie;
import com.example.android.popular_movies.models.Video;

@Database(entities = {Movie.class, Review.class, Video.class, StarredMovie.class}, version = 1)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    public abstract ReviewDao reviewDao();

    public abstract VideoDao videoDao();

    public abstract StarredMovieDao starredMovieDao();

    private static MyRoomDatabase instance;
    public static MyRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MyRoomDatabase.class,
                    "room-db"
            ).build();
        }
        return instance;
    }

}
