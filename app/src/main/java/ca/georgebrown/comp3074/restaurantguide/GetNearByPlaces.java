package ca.georgebrown.comp3074.restaurantguide;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GetNearByPlaces extends AsyncTask<Object, String, String>
{
    private String googlePlaceData, url;
    private GoogleMap mMap;

    @Override
    protected String doInBackground(Object... objects) {

        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlaceData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return googlePlaceData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearByPlacesList = null;
        DataParser parser = new DataParser();
        nearByPlacesList = parser.parse(s);
        DisplayNearByPlaces(nearByPlacesList);
    }

    private void DisplayNearByPlaces(List<HashMap<String, String>> nearByPlacesList)
    {
        for (int i = 0; i < nearByPlacesList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearByPlacesList.get(i);

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble(Objects.requireNonNull(googlePlace.get("lat")));
            double lng = Double.parseDouble(Objects.requireNonNull(googlePlace.get("lng")));

            LatLng latLng = new LatLng(lat, lng);

            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        }

    }
}
