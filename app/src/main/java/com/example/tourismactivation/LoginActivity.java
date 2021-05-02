package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements AsyncCallback<BackendlessUser> {
    TextInputLayout loginEmailTextField,loginPasswordTextField;
    SharedPreferences pref;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        loginEmailTextField = findViewById(R.id.loginEmailTextField);
        loginPasswordTextField = findViewById(R.id.loginPasswordTextField);
        loginButton = findViewById(R.id.loginButton);

    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void login(View view) {

        loginButton.setEnabled(false);
        //checking email validation
        if (!(Patterns.EMAIL_ADDRESS.matcher(loginEmailTextField.getEditText().getText().toString()).matches())) {
            loginEmailTextField.setErrorEnabled(true);
            loginEmailTextField.setError("wrong Email Address");
            loginButton.setEnabled(true);

            return;

        }

        //checking email validation
        if (loginPasswordTextField.getEditText().getText().length() < 8) {
            loginPasswordTextField.setErrorEnabled(true);
            loginPasswordTextField.setError("wrong password");
            loginButton.setEnabled(true);
            return;

        }


        String email = loginEmailTextField.getEditText().getText().toString();
        String password = loginPasswordTextField.getEditText().getText().toString();

        Backendless.UserService.login(email,password,this,true);

    }

    @Override
    public void handleResponse(BackendlessUser user) {
        Log.i("userBackendless Data", user.toString());

        String profilePicture = (String) user.getProperty( "profilePicture" );
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("userName", user.getProperty("name").toString());
        editor.putString("userEmail", user.getProperty("email").toString());
        editor.putString("userPhone", user.getProperty("phone").toString());
        editor.putInt("userCountryCode", (Integer) user.getProperty("countryCode"));

        Log.i("user Local Data Saved", user.toString());

        Intent intent;
        if(profilePicture == null)
        {
            intent = new Intent(this, SettingProfilePictureActivity.class);

            // nullify
        }else
        {

            editor.putString("userProfilePicture",user.getProperty("profilePicture").toString());


            intent = new Intent(this, HomeActivity.class);
            // nullify
        }
        editor.apply();
        startActivity(intent);
        intent = null;
        finishAffinity();




    }

    @Override
    public void handleFault(BackendlessFault fault) {
        if(fault.getCode().equals("3003"))
        {
            loginButton.setEnabled(true);
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();

        }
        else
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();

    }


}