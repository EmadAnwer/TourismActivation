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
import java.util.Objects;


public class RegistrationActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener, AsyncCallback<BackendlessUser> {

    TextInputLayout nameTextField, emailTextField, passwordInputLayout, confirmPasswordTextField, phoneTextField;
    TextInputEditText selectedTextInputField ;
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
        countryCodePicker = findViewById(R.id.countryCodePicker);
        root = findViewById(R.id.root);
        nameTextField = findViewById(R.id.loginEmailTextField);
        emailTextField = findViewById(R.id.emailTextField);
        passwordInputLayout = findViewById(R.id.loginPasswordTextField);
        confirmPasswordTextField = findViewById(R.id.confirmPasswordTextField);
        phoneTextField = findViewById(R.id.phoneTextField);


        //set OnFocusChangeListener
        Objects.requireNonNull(nameTextField.getEditText()).setOnFocusChangeListener(this);
        Objects.requireNonNull(emailTextField.getEditText()).setOnFocusChangeListener(this);
        Objects.requireNonNull(passwordInputLayout.getEditText()).setOnFocusChangeListener(this);
        Objects.requireNonNull(confirmPasswordTextField.getEditText()).setOnFocusChangeListener(this);
        Objects.requireNonNull(phoneTextField.getEditText()).setOnFocusChangeListener(this);


        //set TextChangedListener
        nameTextField.getEditText().addTextChangedListener(this);
        emailTextField.getEditText().addTextChangedListener(this);
        passwordInputLayout.getEditText().addTextChangedListener(this);
        confirmPasswordTextField.getEditText().addTextChangedListener(this);
        phoneTextField.getEditText().addTextChangedListener(this);


        // setting CountryCodePicker
        countryCodePicker.registerCarrierNumberEditText(phoneTextField.getEditText());

    }

    // back to Home
    public void back(View view) {
        super.onBackPressed();
    }




    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (selectedTextInputField == null)
            return;

        //to checking password and confirmPassword TextField at the same time
        if (selectedTextInputField.getId() == passwordInputLayout.getEditText().getId()) {
            if (!confirmPasswordTextField.getEditText().getText().toString().isEmpty()) {
                if (confirmPasswordTextField.getEditText().getText().toString().equals(s.toString())) {
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

        /*
        name TextField validation
        -------------------------
            name is valid if
                - more than 3 char
                - in alphabet
         */

        if (selectedTextInputField.getId() == nameTextField.getEditText().getId()) {
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


        }


        /*
        email TextField validation
        -------------------------
            email is valid if
                - follows Patterns.EMAIL_ADDRESS
         */
        else if (selectedTextInputField.getId() == emailTextField.getEditText().getId()) {
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
        }


        /*
        password TextField validation
        -------------------------
            password is valid if
                - more than 8 char

         */


        else if (selectedTextInputField.getId() == passwordInputLayout.getEditText().getId()) {
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
        }


        /*
        confirmPassword TextField validation
        -------------------------
            confirmPassword is valid if
                - confirmPassword == password

         */
        else if (selectedTextInputField.getId() == confirmPasswordTextField.getEditText().getId()) {
            if (s.length() == 0) {
                confirmPasswordTextField.setErrorEnabled(false);
                confirmPasswordTextField.setErrorEnabled(true);

                return;
            }


            if (!passwordInputLayout.getEditText().getText().toString().equals(s.toString())) {

                confirmPasswordTextField.setError("Passwords is not the same");
                return;

            }

            confirmPasswordTextField.setErrorEnabled(false);
            confirmPasswordTextField.setErrorEnabled(true);
        }


        /*
        phone TextField validation
        -------------------------
            phone is valid if
                - isValidFullNumber() = true

         */
        else if (selectedTextInputField.getId() == phoneTextField.getEditText().getId()) {
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

    /*
      check if all Fields are valid or not
      return true if all are valid
      return false if any field is invalid
     */
    boolean checkingAllFieldsValidation() {
        if (nameTextField.getEditText().getText().length() < 3)
            return false;
        if (!(Patterns.EMAIL_ADDRESS.matcher(emailTextField.getEditText().getText().toString()).matches()))
            return false;
        if (passwordInputLayout.getEditText().getText().length() < 8)
            return false;
        if (!passwordInputLayout.getEditText().getText().toString().equals(confirmPasswordTextField.getEditText().getText().toString()))
            return false;
        if (!countryCodePicker.isValidFullNumber())
            return false;

        return true;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //getting selected InputField reference to use it in checking validation
        if (hasFocus) {
            selectedTextInputField = (TextInputEditText) v;
        }

    }

    public void createAccount(View view) {
        if (checkingAllFieldsValidation()) {

            if (mails.contains(emailTextField.getEditText().getText().toString())) {
                emailAlreadyExists();
                return;
            }
            create = (Button) view;
            create.setClickable(false);

            BackendlessUser user = new BackendlessUser();
            user.setProperty("name", nameTextField.getEditText().getText().toString());
            user.setEmail(emailTextField.getEditText().getText().toString());
            user.setPassword(passwordInputLayout.getEditText().getText().toString());
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
            mails.add(emailTextField.getEditText().getText().toString());
        }


       else
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

        create.setClickable(true);

    }

    void emailAlreadyExists()
    {
        emailTextField.setError("this Email Already exists");
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
}