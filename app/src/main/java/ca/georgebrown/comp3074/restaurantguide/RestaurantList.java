package ca.georgebrown.comp3074.restaurantguide;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import java.util.List;

public class RestaurantList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restaurants);

        ListView listView = findViewById(R.id.listViewRestaurants);
        ArrayAdapter<Restaurant> adapter = new RestaurantListAdapter((Context) this, (Integer) RestaurantDataStore.getInstance().getRestaurants());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Restaurant restaurant = adapter.getItem(position);
            // Implement what happens when you click on a restaurant
            // For example, you could start a new Activity to show the details of the selected restaurant
        });
    }
}

class RestaurantListAdapter extends ArrayAdapter<Restaurant> {
    public RestaurantListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public RestaurantListAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public RestaurantListAdapter(@NonNull Context context, int resource, @NonNull Restaurant[] objects) {
        super(context, resource, objects);
    }

    public RestaurantListAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Restaurant[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public RestaurantListAdapter(@NonNull Context context, int resource, @NonNull List<Restaurant> objects) {
        super(context, resource, objects);
    }

    public RestaurantListAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Restaurant> objects) {
        super(context, resource, textViewResourceId, objects);
    }
    // We'll assume you have a basic understanding of how to implement an ArrayAdapter,
    // since that can get quite detailed and is beyond the scope of this example.
    // You would override methods like getCount(), getItem(), getItemId(), and getView().
}
