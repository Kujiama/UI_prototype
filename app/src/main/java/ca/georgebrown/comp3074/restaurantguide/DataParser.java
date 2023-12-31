package ca.georgebrown.comp3074.restaurantguide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser
{
    private HashMap<String, String> getPlace(JSONObject googlePlaceJSON) {
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude;
        String longitude;
        String reference;

        try {
            if (!googlePlaceJSON.isNull("name")) {
                placeName = googlePlaceJSON.getString("name");
            }
            if (!googlePlaceJSON.isNull("vicinity"))
            {
                vicinity = googlePlaceJSON.getString("vicinity");
            }
            latitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJSON.getString("reference");

            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }
        return googlePlaceMap;
    }

    private List<HashMap<String, String>> getAllNearByPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();

        HashMap<String, String> placeMap = null;

        for (int i = 0; i < count; i++) {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            }
            catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return placesList;
    }

    public List<HashMap<String, String>> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return getAllNearByPlaces(jsonArray);
    }
}
