package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.UserService;
import com.example.tourismactivation.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomeActivity extends AppCompatActivity {
    BackendlessUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        Backendless.initApp(this,"936A3E76-2AE2-B9CF-FF53-BADBF82A7500","31DAB2C3-A6AA-4476-8A08-1DB2F1444759");

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
