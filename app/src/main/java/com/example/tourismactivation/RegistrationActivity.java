package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;


public class RegistrationActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener, AsyncCallback<BackendlessUser> {


    TextInputLayout nameTextField, emailTextField, passwordInputLayout, confirmPasswordTextField, phoneTextField;
    TextInputEditText selectedTextInputField, confirmPasswordTextInputField, passwordTextInputField, phoneTextInputField, emailTextInputField, nameTextInputField;
    CountryCodePicker countryCodePicker;
    ConstraintLayout root;
    Button create;
    ArrayList<String> mails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registration);
        //setting ids for views
        nameTextInputField = findViewById(R.id.nameTextInputField);
        emailTextInputField = findViewById(R.id.emailTextInputField);
        passwordTextInputField = findViewById(R.id.passwordTextInputField);
        confirmPasswordTextInputField = findViewById(R.id.confirmPasswordTextInputField);
        phoneTextInputField = findViewById(R.id.phoneTextInputField);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        root = findViewById(R.id.root);
        nameTextField = findViewById(R.id.loginEmailTextField);
        emailTextField = findViewById(R.id.emailTextField);
        passwordInputLayout = findViewById(R.id.loginPasswordTextField);
        confirmPasswordTextField = findViewById(R.id.confirmPasswordTextField);
        phoneTextField = findViewById(R.id.phoneTextField);


        //set OnFocusChangeListener
        nameTextInputField.setOnFocusChangeListener(this);
        emailTextInputField.setOnFocusChangeListener(this);
        passwordTextInputField.setOnFocusChangeListener(this);
        confirmPasswordTextInputField.setOnFocusChangeListener(this);
        phoneTextInputField.setOnFocusChangeListener(this);


        //set TextChangedListener
        nameTextInputField.addTextChangedListener(this);
        emailTextInputField.addTextChangedListener(this);
        passwordTextInputField.addTextChangedListener(this);
        confirmPasswordTextInputField.addTextChangedListener(this);
        phoneTextInputField.addTextChangedListener(this);


        // setting CountryCodePicker
        countryCodePicker.registerCarrierNumberEditText(phoneTextInputField);

    }


    public void back(View view) {
        super.onBackPressed();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (selectedTextInputField == null)
            return;

        if (selectedTextInputField.getId() == passwordTextInputField.getId()) {
            if (!confirmPasswordTextInputField.getText().toString().isEmpty()) {
                if (confirmPasswordTextInputField.getText().toString().equals(s.toString())) {
                    confirmPasswordTextField.setErrorEnabled(false);
                    confirmPasswordTextField.setErrorEnabled(true);
                } else
                    confirmPasswordTextField.setError("Passwords is not the same");


            }

        }

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (selectedTextInputField == null)
            return;


        if (selectedTextInputField.getId() == nameTextInputField.getId()) {
            if (s.length() == 0) {
                nameTextField.setErrorEnabled(false);
                nameTextField.setErrorEnabled(true);

                return;
            }

            if (s.length() < 3) {
                nameTextField.setError("Name should be more 2 characters");
                return;
            }


            nameTextField.setErrorEnabled(false);
            nameTextField.setErrorEnabled(true);


        } else if (selectedTextInputField.getId() == emailTextInputField.getId()) {
            if (s.length() == 0) {
                emailTextField.setErrorEnabled(false);
                emailTextField.setErrorEnabled(true);

                return;
            }


            if (!(Patterns.EMAIL_ADDRESS.matcher(s).matches())) {

                emailTextField.setError("'" + s + "' is not a valid email");
                return;

            }


            emailTextField.setErrorEnabled(false);
            emailTextField.setErrorEnabled(true);
        } else if (selectedTextInputField.getId() == passwordTextInputField.getId()) {
            if (s.length() == 0) {
                passwordInputLayout.setErrorEnabled(false);
                passwordInputLayout.setErrorEnabled(true);

                return;
            }


            if (s.length() < 8) {

                passwordInputLayout.setError("password required be 8 character or more*");
                return;

            }

            passwordInputLayout.setErrorEnabled(false);
            passwordInputLayout.setErrorEnabled(true);
        } else if (selectedTextInputField.getId() == confirmPasswordTextInputField.getId()) {
            if (s.length() == 0) {
                confirmPasswordTextField.setErrorEnabled(false);
                confirmPasswordTextField.setErrorEnabled(true);

                return;
            }


            if (!passwordTextInputField.getText().toString().equals(s.toString())) {

                confirmPasswordTextField.setError("Passwords is not the same");
                return;

            }

            confirmPasswordTextField.setErrorEnabled(false);
            confirmPasswordTextField.setErrorEnabled(true);
        } else if (selectedTextInputField.getId() == phoneTextInputField.getId()) {
            if (s.length() == 0) {
                phoneTextField.setErrorEnabled(false);
                phoneTextField.setErrorEnabled(true);

                return;
            }


            if (!countryCodePicker.isValidFullNumber()) {

                phoneTextField.setError("Phone number is invalid");
                return;

            }

            phoneTextField.setErrorEnabled(false);
            phoneTextField.setErrorEnabled(true);
        }

    }


    boolean checkingAllFieldsValidation() {
        if (nameTextInputField.getText().length() < 3)
            return false;
        if (!(Patterns.EMAIL_ADDRESS.matcher(emailTextInputField.getText().toString()).matches()))
            return false;
        if (passwordTextInputField.getText().length() < 8)
            return false;
        if (!passwordTextInputField.getText().toString().equals(confirmPasswordTextInputField.getText().toString()))
            return false;
        if (!countryCodePicker.isValidFullNumber())
            return false;

        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            selectedTextInputField = (TextInputEditText) v;
        }

    }

    public void createAccount(View view) {
        if (checkingAllFieldsValidation()) {

            if (mails.contains(emailTextInputField.getText().toString())) {
                emailAlreadyExists();
                return;
            }
            create = (Button) view;
            create.setClickable(false);

            BackendlessUser user = new BackendlessUser();
            user.setProperty("name", nameTextInputField.getText().toString());
            user.setEmail(emailTextInputField.getText().toString());
            user.setPassword(passwordTextInputField.getText().toString());
            user.setProperty("phone", countryCodePicker.getFullNumber());
            Backendless.UserService.register(user, this);
        }


    }

    @Override
    public void handleResponse(BackendlessUser response) {
        onBackPressed();
        Toast.makeText(this, "You have successfully registered", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void handleFault(BackendlessFault fault) {

        if (fault.getCode().equals("3033"))
        {
            emailAlreadyExists();
            mails.add(emailTextInputField.getText().toString());
        }


       else
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

        create.setClickable(true);

    }

    void emailAlreadyExists()
    {
        emailTextField.setError("this Email Already exists");
    }

}