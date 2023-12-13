package ca.georgebrown.comp3074.restaurantguide;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDataStore {
    private static RestaurantDataStore instance;
    private List<Restaurant> restaurantList;

    private RestaurantDataStore() {
        // Initialize the list of restaurants
        restaurantList = new ArrayList<>();
    }

    public static RestaurantDataStore getInstance() {
        if (instance == null) {
            instance = new RestaurantDataStore();
        }
        return instance;
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurantList.add(restaurant);
        // In a real app, here you might save the restaurant to a database or other storage mechanism
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public Restaurant getRestaurantByName(String name) {
        for (Restaurant restaurant : restaurantList) {
            if (restaurant.getName().equalsIgnoreCase(name)) {
                return restaurant;
            }
        }
        return null; // If the restaurant with the specified name is not found
    }

    public void updateRestaurant(Restaurant updatedRestaurant) {
        for (int i = 0; i < restaurantList.size(); i++) {
            Restaurant restaurant = restaurantList.get(i);
            if (restaurant.getName().equalsIgnoreCase(updatedRestaurant.getName())) {
                restaurantList.set(i, updatedRestaurant);
                // In a real app, here you might update the restaurant in the database or other storage mechanism
                return;
            }
        }
        // Handle case where the updated restaurant was not found
    }

    public void deleteRestaurant(String name) {
        for (int i = 0; i < restaurantList.size(); i++) {
            Restaurant restaurant = restaurantList.get(i);
            if (restaurant.getName().equalsIgnoreCase(name)) {
                restaurantList.remove(i);
                // In a real app, here you might delete the restaurant from the database or other storage mechanism
                return;
            }
        }
        // Handle case where the restaurant to delete was not found
    }

    public Object getRestaurants() {
        return null;
    }

    // Other methods and functionalities specific to managing restaurant data
}