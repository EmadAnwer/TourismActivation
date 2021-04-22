package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.tourismactivation.molde.Governorates;
import com.example.tourismactivation.molde.Images;
import com.example.tourismactivation.recyclerView.GovernoratesRecyclerViewAdapter;
import com.example.tourismactivation.recyclerView.PlaceImageSliderAdapter;
import com.example.tourismactivation.ui.pageAdapter.HomeFragmentPageAdapter;
import com.example.tourismactivation.ui.pageAdapter.PlacePagerAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import xyz.hanks.library.bang.SmallBangView;

public class PlaceActivity extends AppCompatActivity {
    NavigationTabStrip placeNavTabStrip;
    ViewPager placeViewPager;
    SmallBangView imageView;
    PlacePagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        //views
        placeViewPager = findViewById(R.id.placeViewPager);
        imageView = findViewById(R.id.imageViewAnimation);
        placeNavTabStrip = findViewById(R.id.placeNavTabStrip);

        adapter = new PlacePagerAdapter(this,getSupportFragmentManager());
        placeViewPager.setAdapter(adapter);
        placeNavTabStrip.setViewPager(placeViewPager);



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
}