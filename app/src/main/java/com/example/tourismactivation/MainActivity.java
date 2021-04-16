package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


    }


    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);


        // nullify
        intent = null;
    }

    public void create(View view) {

        Intent intent = new Intent(this, RegistrationActivity.class);

        startActivity(intent);

        // nullify
        intent = null;
    }


}