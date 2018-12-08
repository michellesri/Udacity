package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject jsonObj = new JSONObject(json);
        JSONObject name = jsonObj.getJSONObject("name");
        String mainName = name.getString("mainName"); // "ham and cheese sandwich"
        String description = jsonObj.getString("description");
        String placeOfOrigin = jsonObj.getString("placeOfOrigin");
        String image = jsonObj.getString("image");

        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
        ArrayList<String> alsoKnownAsList = new ArrayList<>();
        for (int i = 0; i < alsoKnownAs.length(); i++) {
            alsoKnownAsList.add(alsoKnownAs.getString(i));
        }

        JSONArray ingredients = jsonObj.getJSONArray("ingredients");
        ArrayList<String> ingredientsList = new ArrayList<>();
        for(int i = 0; i < ingredients.length(); i++) {
            ingredientsList.add(ingredients.getString(i));
        }

        return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin,
                description, image, ingredientsList);

        }

}
