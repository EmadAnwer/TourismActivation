package com.example.tourismactivation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tourismactivation.ui.pageAdapter.HomeFragmentPageAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Calendar;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment:";
    SharedPreferences pref;
    String name,profilePic;


    NavigationTabStrip navTabStrip;
    ShapeableImageView profilePictureImageView;
    TextView welcomeTextView,userNameFragmentTextView;

    ViewPager homeViewPager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        //initialize SharedPreferences
        pref = this.getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        //getting user's data from Shared Preference
        name = pref.getString("userName", "");
        profilePic = pref.getString("userProfilePicture", "");
        Log.i(TAG, "getting userName: "+name);
        Log.i(TAG, "getting profilePicture: "+profilePic);


        super.onCreate(savedInstanceState);

    }

    HomeFragmentPageAdapter homeFragmentPageAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //getting views id
        homeViewPager = view.findViewById(R.id.homeViewPager);
        profilePictureImageView = view.findViewById(R.id.profilePictureImageView);
        welcomeTextView = view.findViewById(R.id.welcomeTextView);
        userNameFragmentTextView = view.findViewById(R.id.userNameFragmentTextView);
        settingUserViews();

        NavigationTabStrip navigationTabStrip = view.findViewById(R.id.navTabStrip);
        if(homeFragmentPageAdapter == null)
        {
            homeFragmentPageAdapter = new HomeFragmentPageAdapter(getActivity(), getChildFragmentManager());

        }
        homeViewPager.setAdapter(homeFragmentPageAdapter);
        navigationTabStrip.setViewPager(homeViewPager,0);



        return view;
    }


    void settingUserViews()
    {


        welcomeTextView.setText(getTimeMessage());
        userNameFragmentTextView.setText(name);

        Glide.with(getActivity())
                .load(profilePic)
                .fitCenter()
                .into(profilePictureImageView);

    }

    String getTimeMessage()
    {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            return "Good Morning,";
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            return "Good Afternoon,";

        }else if(timeOfDay >= 16 && timeOfDay < 21){
            return "Good Evening,";
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            return "Good Night,";
        }

        else return "Hello,";
    }




}