package com.example.tourismactivation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.bumptech.glide.Glide;
import com.example.tourismactivation.ui.pageAdapter.HomeFragmentPageAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Calendar;


public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment:";
    SharedPreferences pref;
    String name,profilePic;
    ImageView logoutImageView;

    NavigationTabStrip navTabStrip;
    ShapeableImageView profilePictureImageView;
    TextView welcomeTextView,userNameFragmentTextView;

    ViewPager homeViewPager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize SharedPreferences
        pref = this.getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        //getting user's data from Shared Preference
        name = pref.getString("userName", "");
        profilePic = pref.getString("userProfilePicture", "");
        Log.i(TAG, "onCreate: name:"+name);
        Log.i(TAG, "onCreate: name:"+profilePic);


    }
    boolean updated;
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
        logoutImageView = view.findViewById(R.id.logoutImageView);

        name = pref.getString("userName", "");
        profilePic = pref.getString("userProfilePicture", "");

        settingUserViews();
        NavigationTabStrip navigationTabStrip = view.findViewById(R.id.navTabStrip);
        if(homeFragmentPageAdapter == null)
        {
            homeFragmentPageAdapter = new HomeFragmentPageAdapter(getActivity(), getChildFragmentManager());

        }
        homeViewPager.setAdapter(homeFragmentPageAdapter);
        navigationTabStrip.setViewPager(homeViewPager,0);
        profilePictureImageView.setOnClickListener(this);
        logoutImageView.setOnClickListener(this);

        return view;
    }


    void settingUserViews()
    {


        welcomeTextView.setText(getTimeMessage());
        userNameFragmentTextView.setText(name);
        Toast.makeText(getActivity(), "im here image", Toast.LENGTH_SHORT).show();



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


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profilePictureImageView)
        {
            Intent intent;

            intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
            intent = null;
        }
        else if(v.getId() == R.id.logoutImageView)
        {
            userLogout();
        }

    }
    void userLogout(){

        Backendless.UserService.logout(new AsyncCallback<Void>()
        {


            @Override
            public void handleResponse(Void response) {
                // user has been logged out.
                //go back to MainActivity
                Intent intent;

                intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                intent = null;
                getActivity().finishAffinity();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                {

                    // something went wrong and logout failed, to get the error code call fault.getCode()
                    Toast.makeText(getActivity(), "something wrong while logout"+fault.getCode(), Toast.LENGTH_SHORT).show();
                }
            }


        });
    }



}