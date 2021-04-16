package com.example.tourismactivation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tourismactivation.ui.main.HomeFragmentPageAdapter;
import com.example.tourismactivation.ui.main.SectionsPagerAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;


import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {
    NavigationTabStrip navTabStrip;

    ViewPager homeViewPager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    HomeFragmentPageAdapter homeFragmentPageAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewPager = view.findViewById(R.id.homeViewPager);
        NavigationTabStrip navigationTabStrip = view.findViewById(R.id.navTabStrip);


        if(homeFragmentPageAdapter == null)
        {
            homeFragmentPageAdapter = new HomeFragmentPageAdapter(getActivity(), getChildFragmentManager());

        }

        homeViewPager.setAdapter(homeFragmentPageAdapter);
        navigationTabStrip.setViewPager(homeViewPager,0);



        return view;
    }






}