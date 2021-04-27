package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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

import nl.joery.animatedbottombar.AnimatedBottomBar;
/*


 */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    String name, profilePic;
    SharedPreferences pref;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        Backendless.initApp(this, "30A3F936-C7E6-49FF-8FF6-E4ADF602134B", "1C3A9234-2AAF-436B-93B0-B988B72F942C");



        Log.i("data", "name: " + name);
        Log.i("data", "name: " + profilePic);



        checkingUserAuthentication();

        Log.i(TAG, "onCreate: ");
        Log.i("HomeActivity localData:", "onCreate: ");







        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);
        AnimatedBottomBar animatedBottomBar = findViewById(R.id.bottom_bar);
        animatedBottomBar.setupWithViewPager(viewPager);
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
        Toast.makeText(this, "" + userToken, Toast.LENGTH_SHORT).show();

        if (userToken == null || (userToken.equals(""))) {
            Toast.makeText(this, "level2", Toast.LENGTH_SHORT).show();

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
                String updated = response.getProperty("updated").toString();
                if(!updated.equals(pref.getString("updated"," ")))
                {


                    Toast.makeText(HomeActivity.this, "updated", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("userName", response.getProperty("name").toString());
                    editor.putString("userProfilePicture", response.getProperty("profilePicture").toString());
                    editor.putString("updated", response.getProperty("updated").toString());
                    editor.apply();
                }


            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });











    }

    void userLogout(){
        //logout
        Backendless.UserService.logout( new AsyncCallback<Void>()
        {
            public void handleResponse( Void response )
            {
                // user has been logged out.
                checkingUserAuthentication();//go back to MainActivity
            }

            public void handleFault( BackendlessFault fault )
            {
                // something went wrong and logout failed, to get the error code call fault.getCode()
                Toast.makeText(HomeActivity.this, "something wrong while logout", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void intentToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }



}
