package ca.georgebrown.comp3074.restaurantguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btn = findViewById(R.id.newRestaurantBtn);
        btn.setOnClickListener(v -> {
            Intent i = new Intent(Dashboard.this, AddRestaurantActivity.class);
            startActivity(i);
        });
    }


}