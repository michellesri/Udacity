package com.example.android.popular_movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String posterJsonString = intent.getStringExtra("clickedPoster");

        try {
            JSONObject jsonObject = new JSONObject(posterJsonString);
            render(jsonObject);
            System.out.println("poster json string!!!");
            System.out.println(posterJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(posterJsonString);
        // parse the json
        // display data to page

    }

    public void render(JSONObject jsonObject) throws JSONException {
        String title = jsonObject.getString("title");
        String releaseDate = jsonObject.getString("release_date");
        String posterPath = jsonObject.getString("poster_path");
        String moviePoster = MyAdapter.TMDB_BASE_PATH + MyAdapter.POSTER_SIZE + posterPath;
        String voteAverage = jsonObject.get("vote_average").toString();
        String plotSynopsis = jsonObject.getString("overview");

//        System.out.println(title);
//        System.out.println(releaseDate);

        String detailText = title + "\n"
                + releaseDate + "\n"
                + "Vote Average: " + voteAverage + "\n"
                + plotSynopsis;

        TextView detailTv = findViewById(R.id.poster_detail_tv);
        detailTv.setText(detailText);

        ImageView posterIv = findViewById(R.id.poster_iv);
        Picasso.with(this).load(moviePoster).into(posterIv);








    }
}
