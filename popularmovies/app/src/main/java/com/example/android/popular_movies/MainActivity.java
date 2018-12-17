package com.example.android.popular_movies;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String POPULAR_MOVIES_ENDPOINT = "/movie/popular";
    final String TOP_RATED_ENDPOINT = "/movie/top_rated";

    final String API_KEY_QUERY = "?api_key=" + BuildConfig.API_KEY;
    final String BASE_URL = "http://api.themoviedb.org/3";
    final String POPULAR_MOVIES_URL = BASE_URL + POPULAR_MOVIES_ENDPOINT + API_KEY_QUERY;
    final String TOP_RATED_URL = BASE_URL + TOP_RATED_ENDPOINT + API_KEY_QUERY;

    // for displaying posters using picasso

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ApiHandler.apiRequest(this, POPULAR_MOVIES_URL);
        
    }

    public void onDataReceived(JSONObject jsonObject) throws JSONException {
        JSONArray jsonResults = jsonObject.getJSONArray("results");
        List<JSONObject> results = new ArrayList<>();
        for (int i = 0; i < jsonResults.length(); i++) {
            results.add(jsonResults.getJSONObject(i));
        }

        mAdapter = new MyAdapter(results, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String endpoint = null;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_top_rated:
                if (checked)
                    endpoint = TOP_RATED_URL;

                    break;
            case R.id.radio_popular_movies:
                if (checked)
                    endpoint = POPULAR_MOVIES_URL;
                    break;
        }
        ApiHandler.apiRequest(this, endpoint);

    }


}
