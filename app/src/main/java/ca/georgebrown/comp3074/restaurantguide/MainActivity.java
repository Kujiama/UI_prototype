package ca.georgebrown.comp3074.restaurantguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView logoImageView = findViewById(R.id.logoImageView);
        logoImageView.setImageResource(R.drawable.dinewise);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // Handle navigation item clicks here
            if (id == R.id.nav_home) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_profile) {
                if (isUserLoggedIn()) {
                    // User is logged in, navigate to the profile
                    Intent intent = new Intent(this, ProfileActivity.class); //
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, SignInActivity.class);
                    startActivity(intent);
                }
            } else if (id == R.id.nav_search) {
                    Intent intent = new Intent(this, SearchActivity.class);
                    startActivity(intent);
                }
            return true;
        });
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("SAVE_USER_DETAIL", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isSignedIn", false);
    }
}