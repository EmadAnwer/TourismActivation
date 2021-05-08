package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tourismactivation.molde.Places;
import com.example.tourismactivation.molde.Tickets;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    List<Places> favoritePlaces = new ArrayList<>();

    List<Tickets> userFavorites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
    }
}