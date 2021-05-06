package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.bumptech.glide.Glide;
import com.example.tourismactivation.molde.Governorates;
import com.example.tourismactivation.molde.Images;
import com.example.tourismactivation.recyclerView.GovernoratesRecyclerViewAdapter;
import com.example.tourismactivation.recyclerView.PlaceImageSliderAdapter;
import com.example.tourismactivation.ui.pageAdapter.HomeFragmentPageAdapter;
import com.example.tourismactivation.ui.pageAdapter.PlacePagerAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import xyz.hanks.library.bang.SmallBangView;

public class PlaceActivity extends AppCompatActivity implements View.OnClickListener {
    NavigationTabStrip placeNavTabStrip;
    ViewPager placeViewPager;
    SmallBangView imageView;
    ShapeableImageView place_ImageView;
    PlacePagerAdapter adapter;
    SharedPreferences pref;
    String coverImage,name,id;
    Toolbar placeToolbar;

    CollapsingToolbarLayout placeCollapsingToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        pref = getSharedPreferences("placesPref", Context.MODE_PRIVATE);
        coverImage = pref.getString("coverImage","error");
        name = pref.getString("placeName","error");
        id = pref.getString("placeID","error");

        //views
        placeViewPager = findViewById(R.id.placeViewPager);
        imageView = findViewById(R.id.imageViewAnimation);
        placeNavTabStrip = findViewById(R.id.placeNavTabStrip);
        place_ImageView = findViewById(R.id.place_ImageView);
        placeCollapsingToolbar = findViewById(R.id.placeCollapsingToolbar);
        placeToolbar = findViewById(R.id.placeToolbar);




        //setting cover and place name
        placeCollapsingToolbar.setTitle(name);
        Glide.with(this)
                .asBitmap()
                .load(coverImage)
                .into(place_ImageView);


        adapter = new PlacePagerAdapter(this,getSupportFragmentManager());
        placeViewPager.setAdapter(adapter);
        placeNavTabStrip.setViewPager(placeViewPager);

        placeToolbar.setNavigationOnClickListener(this);

    }


    public void addToFavorite(View view) {
        if (imageView.isSelected()) {
            imageView.setSelected(false);
        } else {
            // if not selected only
            // then show animation.
            imageView.setSelected(true);
            imageView.likeAnimation();
    }
}

    @Override
    public void onClick(View v) {
        //clear and nullify
        finish();
        onBackPressed();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}