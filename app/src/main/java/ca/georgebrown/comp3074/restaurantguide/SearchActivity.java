package ca.georgebrown.comp3074.restaurantguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements OnMapReadyCallback {


    GoogleMap map;
    MapView mapView;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);

        searchView.setOnClickListener(v -> onSearchViewClicked(View
                .inflate(this, R.layout.activity_search, null)));

        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (id == R.id.nav_profile) {
                Intent intent = isUserLoggedIn() ? new Intent(this, ProfileActivity.class) : new Intent(this, SignInActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_search) {
                startActivity(new Intent(this, SearchActivity.class));
            }
            return true;
        });
    }
    public void onSearchViewClicked(View view) {
        // Handle the click event for the SearchView here
        // This method will be called when the SearchView area is clicked
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { // When user clicks the search button
                searchLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { // When user types any character
                return false;
            }
        });
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);

        Toast.makeText(this, "SearchView Clicked", Toast.LENGTH_SHORT).show();
    }
    private void searchLocation(String query) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(query, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();

                // Move the map camera to the searched location
                LatLng location = new LatLng(latitude, longitude);
                if (map != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12)); // Zoom level 15 (adjust as needed)
                } else {
                    Toast.makeText(this, "Map not initialized", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Handle case when no location found
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isUserLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("SAVE_USER_DETAIL", MODE_PRIVATE);
        return preferences.getBoolean("isSignedIn", false);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng initialLocation = new LatLng(43.6518008, -79.3835899); //Toronto
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 12));
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}

