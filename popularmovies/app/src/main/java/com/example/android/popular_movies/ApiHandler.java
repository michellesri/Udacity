package com.example.android.popular_movies;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

class ApiHandler {

    //api request for movies
    static void apiRequest(final MainActivity mainActivity, String url) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mainActivity);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONObject jsonResponse = new JSONObject(response);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    mainActivity.onDataReceived(jsonResponse);

                                }
                            }).start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Request for JSON data failed");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    //api request for trailer and reviews for one specific movie
    static void apiRequest(final String uniqueId, final DetailActivity detailActivity, String url) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(detailActivity);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONObject jsonResponse = new JSONObject(response);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    detailActivity.onDataReceived(uniqueId, jsonResponse);

                                }
                            }).start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Request for JSON data failed");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
