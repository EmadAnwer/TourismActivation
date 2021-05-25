package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class ZoomImageActivity extends AppCompatActivity {
    SharedPreferences pref;
    String imageUrl;
    PhotoView zoomPhotoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        zoomPhotoView = findViewById(R.id.zoomPhotoView);
        pref = getSharedPreferences("GalleryPref", Context.MODE_PRIVATE);
        imageUrl = pref.getString("Image","error");

        //setting cover and place name
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(zoomPhotoView);

    }
}