package ca.georgebrown.comp3074.restaurantguide;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class AddRestaurantActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        final EditText nameInput = findViewById(R.id.editTextRestaurantName);
        final EditText locationInput = findViewById(R.id.editTextLocation);
        final RatingBar ratingInput = findViewById(R.id.ratingBar);
        final EditText dateInput = findViewById(R.id.editTextDateVisited);
        final EditText descriptionInput = findViewById(R.id.editTextDescription);
        final EditText tagsInput = findViewById(R.id.editTextTags);
        Button saveButton = findViewById(R.id.buttonSaveRestaurant);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String location = locationInput.getText().toString();
                float rating = ratingInput.getRating();
                String date = dateInput.getText().toString();
                String description = descriptionInput.getText().toString();
                List<String> tags = Arrays.asList(tagsInput.getText().toString().split("\\s*,\\s*"));
                Restaurant newRestaurant = new Restaurant(name, location, rating, date, description, tags);
                RestaurantDataStore.getInstance().addRestaurant(newRestaurant);

                // After saving, you could clear the fields or show a confirmation message
                // Clear fields example:
                nameInput.setText("");
                locationInput.setText("");
                ratingInput.setRating(0);
                dateInput.setText("");
                descriptionInput.setText("");
                tagsInput.setText("");
            }
        });

    }
}