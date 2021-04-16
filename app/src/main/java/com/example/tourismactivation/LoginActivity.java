package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

        //checking email validation
        if (!(Patterns.EMAIL_ADDRESS.matcher(loginEmailTextField.getEditText().getText().toString()).matches())) {
            loginEmailTextField.setErrorEnabled(true);
            loginEmailTextField.setError("wrong Email Address");
            return;

        }

        //checking email validation
        if (loginPasswordTextField.getEditText().getText().length() < 8) {
            loginPasswordTextField.setErrorEnabled(true);
            loginPasswordTextField.setError("wrong password");
            return;

        }


        String email = loginEmailTextField.getEditText().getText().toString();
        String password = loginPasswordTextField.getEditText().getText().toString();

        Backendless.UserService.login(email,password,this,true);

    }

    @Override
    public void handleResponse(BackendlessUser response) {
        Log.i("login", response.toString());

        finish();

    }

    @Override
    public void handleFault(BackendlessFault fault) {
        if(fault.getCode().equals("3003"))
        {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();

        }
            Log.i("faild", fault.toString());

        Toast.makeText(this, "faild", Toast.LENGTH_SHORT).show();

    }
}