package com.example.android.popular_movies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popular_movies.db.MyRoomDatabase;
import com.example.android.popular_movies.db.StarredMovieDao;
import com.example.android.popular_movies.models.Movie;
import com.example.android.popular_movies.models.Review;
import com.example.android.popular_movies.models.StarredMovie;
import com.example.android.popular_movies.models.Video;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    final String UNIQUE_VIDEO_ID = "videoId";
    final String UNIQUE_REVIEW_ID = "reviewId";
    String mVideoKey;

    Button trailerButton;

    private StarredMovieDao mStarredMovieDao;

    ImageButton mStarButtonOff;
    ImageButton mStarButtonOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();

        mStarredMovieDao = MyRoomDatabase.getInstance(DetailActivity.this).starredMovieDao();
        mStarButtonOff = findViewById(R.id.star_button_off);
        mStarButtonOn = findViewById(R.id.star_button_on);

        final String clickedMovieId = intent.getStringExtra("clickedMovieId");
        configureTrailerButton(clickedMovieId);
        configureReview(clickedMovieId);
        configureStarredMovieButton(clickedMovieId);

        LiveData<Movie> movie = MyRoomDatabase.getInstance(this).movieDao().getMovieById(clickedMovieId);
        movie.observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                render(movie);
            }
        });

        final StarredMovie starredMovie = new StarredMovie(clickedMovieId);

        mStarButtonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mStarredMovieDao.insertStarredMovie(starredMovie);
                    }
                }).start();
            }
        });


        mStarButtonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mStarredMovieDao.delete(starredMovie);
                    }
                }).start();


            }
        });


        trailerButton = findViewById(R.id.trailer_button);
        trailerButton.setEnabled(false);

    }

    public void render(Movie movie) {

        String detailText = movie.title + "\n"
                + movie.releaseDate + "\n"
                + "Vote Average: " + movie.voteAverage + "\n"
                + movie.plotSynopsis;

        TextView detailTv = findViewById(R.id.poster_detail_tv);
        detailTv.setText(detailText);

        ImageView posterIv = findViewById(R.id.poster_iv);
        Picasso.with(this).load(movie.moviePoster).into(posterIv);
    }


    public void onDataReceived(String uniqueId, JSONObject jsonObject) {
        String movieId;
        try {
            movieId = jsonObject.getString("id");
            if (uniqueId.equals(UNIQUE_VIDEO_ID)) {
                JSONArray results = jsonObject.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    if (item.getString("site").equals("YouTube") &&
                            item.getString("type").equals("Trailer")) {
                        mVideoKey = item.getString("key");
                        Video video = new Video(movieId, mVideoKey);
                        MyRoomDatabase.getInstance(this).videoDao().insert(video);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                trailerButton.setEnabled(true);
                            }
                        });
                    }
                }
            } else if (uniqueId.equals(UNIQUE_REVIEW_ID)) {
                JSONArray results = jsonObject.getJSONArray("results");
                List<Review> reviews = new ArrayList<>();

                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    String reviewId = item.getString("id");
                    // movie id from above
                    String author = item.getString("author");
                    String content = item.getString("content");
                    Review review = new Review(reviewId, movieId, author, content);
                    reviews.add(review);

                }
                MyRoomDatabase.getInstance(this).reviewDao().insertAll(reviews);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void configureTrailerButton(String id) {


        ApiHandler.apiRequest(UNIQUE_VIDEO_ID, this,
                MainActivity.BASE_URL + "/movie/" + id + "/videos" + MainActivity.API_KEY_QUERY);

        Button trailerButton = findViewById(R.id.trailer_button);
        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + mVideoKey));
                startActivity(intent);

            }
        });
    }

    public void configureReview(final String movieId) {


        ApiHandler.apiRequest(UNIQUE_REVIEW_ID, this,
                MainActivity.BASE_URL + "/movie/" + movieId + "/reviews" + MainActivity.API_KEY_QUERY);

        Button reviewButton = findViewById(R.id.reviews_button);
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                intent.putExtra("movieId", movieId);
                startActivity(intent);
            }
        });
    }

    public void configureStarredMovieButton(final String movieId) {

        LiveData<List<StarredMovie>> starredMovies = mStarredMovieDao.getAllStarredMovies();
        starredMovies.observe(this, new Observer<List<StarredMovie>>() {
            @Override
            public void onChanged(@Nullable List<StarredMovie> starredMovies) {
                if (starredMovies == null) {
                    return;
                }
                boolean found = false;
                for (StarredMovie starredMovie : starredMovies) {
                    if (starredMovie.movieId.equals(movieId)) {
                        found = true;
                    }
                }
                if (found) {
                    mStarButtonOff.setVisibility(View.GONE);
                    mStarButtonOn.setVisibility(View.VISIBLE);
                } else {
                    mStarButtonOff.setVisibility(View.VISIBLE);
                    mStarButtonOn.setVisibility(View.GONE);
                }
            }
        });
    }
}
