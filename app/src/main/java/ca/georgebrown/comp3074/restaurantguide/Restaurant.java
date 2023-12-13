package ca.georgebrown.comp3074.restaurantguide;

import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    private float rating;
    private String dateVisited;
    private String description;
    private List<String> tags;

    public Restaurant(String name, String location, float rating, String dateVisited, String description, List<String> tags) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.dateVisited = dateVisited;
        this.description = description;
        this.tags = tags;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDateVisited() {
        return dateVisited;
    }

    public void setDateVisited(String dateVisited) {
        this.dateVisited = dateVisited;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}