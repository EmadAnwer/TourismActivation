package com.example.tourismactivation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.bumptech.glide.Glide;
import com.example.tourismactivation.ui.pageAdapter.HomeFragmentPageAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.android.material.imageview.ShapeableImageView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;


public class HomeFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private static final String TAG = "HomeFragment:";
    SharedPreferences pref;
    String name,profilePic;
    ImageView optionsImageView;
    PopupMenu options;
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
        optionsImageView = view.findViewById(R.id.optionsImageView);

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
        optionsImageView.setOnClickListener(this);

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


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profilePictureImageView)
        {
            Intent intent;

            intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
            intent = null;
        }
        else if(v.getId() == R.id.optionsImageView) {
            int lang = pref.getInt("appLanguage", -1);

            options = null;
            options = new PopupMenu(this.getContext(),v);
            options.setOnMenuItemClickListener(this);
            options.inflate(R.menu.home_options_menu);
            if(lang != -1)
            {
                final MenuItem language = options.getMenu().getItem(2).getSubMenu().getItem(lang);
                language.setCheckable(true);
                language.setChecked(true);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                options.setForceShowIcon(true);
            }
            else
            {
                try {
                    Field popupField = PopupMenu.class.getDeclaredField("mPopup");
                    popupField.setAccessible(true);
                    Object o = popupField.get(options);
                    assert o != null;
                    o.getClass()
                            .getDeclaredMethod("setForceShowIcon", boolean.class)
                            .invoke(o,true);
                } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            options.show();
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



    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.favoritesItem)
            Toast.makeText(getContext(), "favoritesItem", Toast.LENGTH_SHORT).show();
        else if(id == R.id.ticketsItem)
            Toast.makeText(getContext(), "ticketsItem", Toast.LENGTH_SHORT).show();

        else if(id == R.id.languagesItemAR)
        {

            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("appLanguage", 1);
            editor.apply();

            Toast.makeText(getContext(), "AR", Toast.LENGTH_SHORT).show();

        }
        else if(id == R.id.languagesItemEN)
        {

            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("appLanguage", 0);
            editor.apply();

            Toast.makeText(getContext(), "EN", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.logoutItem)
            userLogout();


        return true;
    }
}