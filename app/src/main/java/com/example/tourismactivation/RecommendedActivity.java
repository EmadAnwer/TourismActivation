package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.tourismactivation.ui.pageAdapter.PlacePagerAdapter;
import com.example.tourismactivation.ui.pageAdapter.RecommendedPagerAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.imageview.ShapeableImageView;

import xyz.hanks.library.bang.SmallBangView;

public class RecommendedActivity extends AppCompatActivity implements View.OnClickListener {

    RecommendedPagerAdapter adapter;
    SharedPreferences pref;
    String coverImage,name;
    Toolbar recommendedToolbar;

    ShapeableImageView recommended_ImageView;
    CollapsingToolbarLayout recommendedCollapsingToolbar;
    NavigationTabStrip recommendedNavTabStrip;
    ViewPager recommendedViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended);
        pref = getSharedPreferences("recommendedPref", Context.MODE_PRIVATE);
        coverImage = pref.getString("coverImage","error");
        name = pref.getString("recommendedName","error");

        //views id's
        recommended_ImageView = findViewById(R.id.recommended_ImageView);
        recommendedCollapsingToolbar = findViewById(R.id.recommendedCollapsingToolbar);
        recommendedNavTabStrip = findViewById(R.id.recommendedNavTabStrip);
        recommendedViewPager = findViewById(R.id.recommendedViewPager);
        recommendedToolbar = findViewById(R.id.recommendedToolbar);



        //setting cover and place name
        recommendedCollapsingToolbar.setTitle(name);
        Glide.with(this)
                .asBitmap()
                .load(coverImage)
                .into(recommended_ImageView);

        recommendedToolbar.setNavigationOnClickListener(this);

        adapter = new RecommendedPagerAdapter(this,getSupportFragmentManager());
        recommendedViewPager.setAdapter(adapter);
        recommendedNavTabStrip.setViewPager(recommendedViewPager);
    }

    @Override
    public void onClick(View v) {
        super.onBackPressed();
    }
}