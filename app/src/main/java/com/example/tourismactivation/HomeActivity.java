package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.bumptech.glide.load.engine.Resource;
import com.example.tourismactivation.molde.Places;
import com.example.tourismactivation.ui.pageAdapter.SectionsPagerAdapter;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import nl.joery.animatedbottombar.AnimatedBottomBar;
/*


 */
public class HomeActivity extends AppCompatActivity {

    int processors = Runtime.getRuntime().availableProcessors();
    boolean updated;
    ExecutorService pool = Executors.newFixedThreadPool(processors);
    SharedPreferences pref;
    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        constants.getLANGUAGE(this);
        Locale locale;
        if(constants.LANGUAGE == constants.AR)
        {
            locale = new Locale("ar");

        }
        else
        {
            locale = new Locale("en");
        }
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());

        setContentView(R.layout.activity_home);
        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        Backendless.initApp(this, "31908FE0-A688-43D5-879D-B815B9404108", "2025C2E4-180B-492E-9BC0-2916C99A2851");

        pool.execute(this::checkingUserAuthentication);


        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        AnimatedBottomBar animatedBottomBar = findViewById(R.id.bottom_bar);
        animatedBottomBar.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        updated = pref.getBoolean("userProfileUpdated",false);

        if(updated)
        {
            viewPager.setAdapter(null);
            viewPager.setAdapter(sectionsPagerAdapter);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("userProfileUpdated", false);
            editor.apply();
        }

        super.onResume();



    }

    void checkingUserAuthentication() {
        /*
        level1
        ---------

        cases
        - new user
        - first time using app
        - after logout by user

        expected
        - userToken = null
        - userToken = ""
         */

        String userToken = UserTokenStorageFactory.instance().getStorage().get();


        if (userToken == null || (userToken.equals(""))) {
            intentToMainActivity();
            return;
        }

        /*
        level2
        ---------

        cases
        - after logout by admin



        expected
        - userToken is expired

         */
        String currentUserId = Backendless.UserService.loggedInUser();
        if(currentUserId == null)
        {

            // clearing token
            userLogout();
            intentToMainActivity();
            return;
        }


        Backendless.UserService.findById(currentUserId, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {


                if(response == null)
                {
                    // clearing token
                    userLogout();
                    intentToMainActivity();
                }
                assert response != null;

                if(response.getProperty("updated") ==null)
                {
                    //TODO
                    return;
                }

                String updated = response.getProperty("updated").toString();
                if(!updated.equals(pref.getString("updated"," ")))
                {


                    Toast.makeText(HomeActivity.this, "updated", Toast.LENGTH_SHORT).show();

                    pool.execute(() -> updateHomeSharedPreference(response));
                }


            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });



        currentUserId =null;







    }

    void userLogout(){
        //logout
        Backendless.UserService.logout( new AsyncCallback<Void>()
        {


            @Override
            public void handleResponse(Void response) {
                // user has been logged out.
                checkingUserAuthentication();//go back to MainActivity
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(HomeActivity.this,getString(R.string.wrong_logout), Toast.LENGTH_SHORT).show();
                // something went wrong and logout failed, to get the error code call fault.getCode()
            }


        });
    }


    void intentToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    void intentToSettingProfilePictureActivity() {
        Intent intent = new Intent(this, SettingProfilePictureActivity.class);
        startActivity(intent);
        finish();
    }



    void updateHomeSharedPreference(BackendlessUser user) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("updated", user.getProperty("updated").toString());
        editor.putString("userName", user.getProperty("name").toString());
        editor.putString("userEmail", user.getProperty("email").toString());
        editor.putString("userPhone", user.getProperty("phone").toString());
        editor.putInt("userCountryCode", (Integer) user.getProperty("countryCode"));
        editor.putString("userProfilePicture",user.getProperty("profilePicture").toString());
        editor.putBoolean("userProfileUpdated", true);

        editor.apply();
    }




}
