package ca.georgebrown.comp3074.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // Handle navigation item clicks here
            if (id == R.id.nav_home) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_profile) {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_search) {
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            }
            return true;
        });

        // Find TextViews for displaying user information
        TextView userNameTextView = findViewById(R.id.user_name_text_view);
        TextView userEmailTextView = findViewById(R.id.user_email_text_view);

        // Retrieve user details from SharedPreferences or Intent
        SharedPreferences preferences = getSharedPreferences("YOUR_PREFS_NAME", MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
        String userEmail = preferences.getString("userEmail", "");

        // Display user details in TextViews
        userNameTextView.setText(getString(R.string.name_placeholder, userName));
        userEmailTextView.setText(getString(R.string.email_placeholder, userEmail));


    }

    public void signOut(View view) {

        GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
                .addOnCompleteListener(this, task -> {
                    // Sign-out successful, go back to the sign-in page or perform other actions
                    Intent intent = new Intent(this, SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Optional: Finish the ProfileActivity
                });
    }
}
