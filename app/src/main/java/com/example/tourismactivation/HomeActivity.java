package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.example.tourismactivation.ui.pageAdapter.SectionsPagerAdapter;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomeActivity extends AppCompatActivity {
    BackendlessUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Backendless.initApp(this,"30A3F936-C7E6-49FF-8FF6-E4ADF602134B","1C3A9234-2AAF-436B-93B0-B988B72F942C");

        /*
        user = Backendless.UserService.CurrentUser();
        if(user == null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            // nullify
            intent = null;
        }


         */


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);
        AnimatedBottomBar animatedBottomBar = findViewById(R.id.bottom_bar);
        animatedBottomBar.setupWithViewPager(viewPager);
    }


}
