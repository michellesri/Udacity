package com.example.android.sunshine.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;

public class SunshineSyncTask {

    synchronized public static void syncWeather(Context context) {

        try {
            String jsonDataString = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.getUrl(context));
            ContentValues[] values = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, jsonDataString);
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = new Uri.Builder().authority(WeatherContract.CONTENT_AUTHORITY).path(WeatherContract.PATH_WEATHER).build();
            contentResolver.delete(uri, null, null);
            contentResolver.bulkInsert(uri, values);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
}