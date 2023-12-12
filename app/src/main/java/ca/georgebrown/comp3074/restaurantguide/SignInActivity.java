package ca.georgebrown.comp3074.restaurantguide;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private GoogleSignInClient googleSignInClient;

    private ActivityResultLauncher<Intent> signInLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken("648747353941-pnurtq9ctoekq6471a2infopnruvpcvo.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        signInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data));
                    }
                });

        SignInButton signInButton = findViewById(R.id.google_sign_in_button);
        signInButton.setOnClickListener(view -> signIn());


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // Handle navigation item clicks here
            if (id == R.id.nav_home) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_profile) {
                Intent intent = new Intent(SignInActivity.this, SignInActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_search) {
                Intent intent = new Intent(SignInActivity.this, SearchActivity.class);
                startActivity(intent);
            }

            return true;
        });

    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        signInLauncher.launch(signInIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Uri data = intent.getData();
        if (data != null && "ca.georgebrown.comp3074.restaurantguide".equals(data.getScheme())) {
            // Handle the custom URI data here, usually after a Google Sign-In flow completion
            // Extract necessary information from the URI if needed
            String code = data.getQueryParameter("code"); // Example: Extract code parameter from URI
            Log.d("SignInActivity", "Code: " + code);
        }
    }


        private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String userName = account.getDisplayName();
            String userEmail = account.getEmail();

            saveUserDetails(userName, userEmail);

            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Sign-in successful!", Toast.LENGTH_SHORT).show();

        } catch (ApiException e) {
            Toast.makeText(this, "Sign-in failed. Error code: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
            Log.e("SignInActivity", "Sign-in failed. Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void saveUserDetails(String userName, String userEmail) {
        SharedPreferences preferences = getSharedPreferences("SAVE_USER_DETAIL", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isSignedIn", true);
        editor.putString("userName", userName);
        editor.putString("userEmail", userEmail);
        editor.apply();
    }

}
