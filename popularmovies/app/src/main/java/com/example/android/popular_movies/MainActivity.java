package com.example.android.popular_movies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import com.example.android.popular_movies.db.MyRoomDatabase;
import com.example.android.popular_movies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static String POPULAR_MOVIES_ENDPOINT = "/movie/popular";
    final static String TOP_RATED_ENDPOINT = "/movie/top_rated";

    final static String API_KEY_QUERY = "?api_key=" + BuildConfig.API_KEY;
    final static String BASE_URL = "http://api.themoviedb.org/3";
    final static String POPULAR_MOVIES_URL = BASE_URL + POPULAR_MOVIES_ENDPOINT + API_KEY_QUERY;
    final static String TOP_RATED_URL = BASE_URL + TOP_RATED_ENDPOINT + API_KEY_QUERY;

    // for displaying posters using picasso

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ApiHandler.apiRequest(this, POPULAR_MOVIES_URL);

        LiveData<List<Movie>> movies = MyRoomDatabase.getInstance(this).movieDao().getAllMovies();
        movies.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mAdapter = new MovieAdapter(movies, MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }

    public void onDataReceived(JSONObject apiResponse) {
        JSONArray jsonResults;
        try {
            jsonResults = apiResponse.getJSONArray("results");
            List<Movie> movies = new ArrayList<>();
            for (int i = 0; i < jsonResults.length(); i++) {

                JSONObject item = jsonResults.getJSONObject(i);
                String id = item.getString("id");

                String title = item.getString("title");
                String releaseDate = item.getString("release_date");
                String posterPath = item.getString("poster_path");
                String moviePoster = MovieAdapter.TMDB_BASE_PATH + MovieAdapter.POSTER_SIZE + posterPath;
                String voteAverage = item.get("vote_average").toString();
                String plotSynopsis = item.getString("overview");

                Movie movie = new Movie(id, title, releaseDate, posterPath, moviePoster, voteAverage, plotSynopsis);
                movies.add(movie);
            }

            MyRoomDatabase myRoomDatabase = MyRoomDatabase.getInstance(this);
            myRoomDatabase.movieDao().insertAll(movies);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        String endpoint = null;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_top_rated:
                endpoint = TOP_RATED_URL;
                break;
            case R.id.radio_popular_movies:
                endpoint = POPULAR_MOVIES_URL;
                break;
            case R.id.radio_user_favorite_movies:
                renderUserFavoriteMovies();
                return;

        }
        ApiHandler.apiRequest(this, endpoint);

    }

    public void renderUserFavoriteMovies() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Movie> starredMovies = MyRoomDatabase.getInstance(MainActivity.this).starredMovieDao().getFavoriteMovies();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new MovieAdapter(starredMovies, MainActivity.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });

            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (((RadioButton) findViewById(R.id.radio_user_favorite_movies)).isChecked()) {
            renderUserFavoriteMovies();
        }
    }
}
