package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.backendless.Backendless;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Backendless.initApp(this,"936A3E76-2AE2-B9CF-FF53-BADBF82A7500","31DAB2C3-A6AA-4476-8A08-1DB2F1444759");

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