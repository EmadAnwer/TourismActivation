package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

public class PlacesActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener {
    RatingBar rating ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        rating = findViewById(R.id.rating);
        rating.setOnRatingBarChangeListener(this);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        Toast.makeText(this, ""+rating, Toast.LENGTH_SHORT).show();
    }
}