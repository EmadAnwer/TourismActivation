package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements AsyncCallback<BackendlessUser> {
    TextInputLayout loginEmailTextField,loginPasswordTextField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        loginEmailTextField = findViewById(R.id.loginEmailTextField);
        loginPasswordTextField = findViewById(R.id.loginPasswordTextField);

    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void login(View view) {
        String email = loginEmailTextField.getEditText().getText().toString();
        String password = loginPasswordTextField.getEditText().getText().toString();

        Backendless.UserService.login(email,password,this,true);

    }

    @Override
    public void handleResponse(BackendlessUser response) {
        Log.i("login", response.toString());
        Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        Log.i("faild", fault.toString());

        Toast.makeText(this, "faild", Toast.LENGTH_SHORT).show();

    }
}