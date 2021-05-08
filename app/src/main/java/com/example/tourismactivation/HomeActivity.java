package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.example.tourismactivation.ui.pageAdapter.SectionsPagerAdapter;

import java.util.Calendar;
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
        setContentView(R.layout.activity_home);
        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        Backendless.initApp(this, "30A3F936-C7E6-49FF-8FF6-E4ADF602134B", "1C3A9234-2AAF-436B-93B0-B988B72F942C");

        pool.execute(this::checkingUserAuthentication);


        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);
        AnimatedBottomBar animatedBottomBar = findViewById(R.id.bottom_bar);
        animatedBottomBar.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        updated = pref.getBoolean("userProfileUpdated",false);
        Toast.makeText(this, "onResume"+updated, Toast.LENGTH_SHORT).show();

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
        runOnUiThread(() -> Toast.makeText(HomeActivity.this,"" + userToken, Toast.LENGTH_SHORT).show());


        if (userToken == null || (userToken.equals(""))) {
            runOnUiThread(() -> Toast.makeText(HomeActivity.this, "level1", Toast.LENGTH_SHORT).show());

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

            Toast.makeText(HomeActivity.this, "level 2 check no user", Toast.LENGTH_SHORT).show();
            // clearing token
            userLogout();
            intentToMainActivity();
            return;
        }


        Backendless.UserService.findById(currentUserId, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Toast.makeText(HomeActivity.this, "level2 user is true", Toast.LENGTH_SHORT).show();

                if(response == null)
                {
                    Toast.makeText(HomeActivity.this, "level2 check", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(HomeActivity.this, "something wrong while logout", Toast.LENGTH_SHORT).show();
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
