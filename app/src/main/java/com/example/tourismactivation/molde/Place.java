package com.example.tourismactivation.molde;

public class Place {
    private int id;
    private float reviews_rate;
    private String category;
    private String name;


    public Place(int id, float reviews_rate, String category, String name) {
        this.id = id;
        this.reviews_rate = reviews_rate;
        this.category = category;
        this.name = name;
    }

    public Place() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getReviews_rate() {
        return reviews_rate;
    }

    public void setReviews_rate(float reviews_rate) {
        this.reviews_rate = reviews_rate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
