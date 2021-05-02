package com.example.tourismactivation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener {
    SharedPreferences pref;
    String name,profilePic,email,phone;
    Uri imageUri;
    int countryCode;
    boolean profileImageChanged;
    String path = "";
    TextView setupProfileSettingsProgressTextView;
    ProgressWheel setupProfileSettingsProgressWheel;



    TextInputLayout nameSettingsTextField, emailSettingsTextField, loginPasswordSettingsTextField, phoneSettingsTextField;
    TextInputEditText selectedTextInputField ;
    ShapeableImageView imgProfileSettings;
    CountryCodePicker countrySettingsCodePicker;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);

        //setting views id's
        nameSettingsTextField = findViewById(R.id.nameSettingsTextField);
        emailSettingsTextField = findViewById(R.id.emailSettingsTextField);
        loginPasswordSettingsTextField = findViewById(R.id.loginPasswordSettingsTextField);
        phoneSettingsTextField = findViewById(R.id.phoneSettingsTextField);
        countrySettingsCodePicker = findViewById(R.id.countrySettingsCodePicker);
        imgProfileSettings = findViewById(R.id.imgProfileSettings);
        saveButton = findViewById(R.id.saveButton);
        countrySettingsCodePicker.registerCarrierNumberEditText(phoneSettingsTextField.getEditText());
        setupProfileSettingsProgressWheel = findViewById(R.id.setupProfileSettingsProgressWheel);
        setupProfileSettingsProgressWheel.stopSpinning();
        setupProfileSettingsProgressTextView = findViewById(R.id.setupProfileSettingsProgressTextView);

        //getting  user data from SharedPreference
        name = pref.getString("userName", "");
        email = pref.getString("userEmail", "");
        profilePic = pref.getString("userProfilePicture", "");
        phone = pref.getString("userPhone", "");
        countryCode = pref.getInt("userCountryCode", 20);

        path =profilePic.substring(profilePic.indexOf("/users-Images/"));
        Log.i("path", "onCreate: "+ path);
        //setting data into views
        nameSettingsTextField.getEditText().setText(name);
        emailSettingsTextField.getEditText().setText(email);
        phoneSettingsTextField.getEditText().setText(phone);
        countrySettingsCodePicker.setCountryForPhoneCode(countryCode);
        Glide.with(this)
                .load(profilePic)
                .fitCenter()
                .into(imgProfileSettings);


        //set TextChangedListener
        nameSettingsTextField.getEditText().addTextChangedListener(this);
        emailSettingsTextField.getEditText().addTextChangedListener(this);
        phoneSettingsTextField.getEditText().addTextChangedListener(this);
        loginPasswordSettingsTextField.getEditText().addTextChangedListener(this);

        nameSettingsTextField.getEditText().setOnFocusChangeListener(this);
        emailSettingsTextField.getEditText().setOnFocusChangeListener(this);
        phoneSettingsTextField.getEditText().setOnFocusChangeListener(this);
        loginPasswordSettingsTextField.getEditText().setOnFocusChangeListener(this);



    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (selectedTextInputField == null)
            return;

        saveButton.setEnabled(isChanged());
        /*
        name TextField validation
        -------------------------
            name is valid if
                - more than 3 char
                - in alphabet
         */

        if (selectedTextInputField.getId() == nameSettingsTextField.getEditText().getId()) {


            if (s.length() < 3) {
                nameSettingsTextField.setError("Name should be more 2 characters");
                return;
            }
            nameSettingsTextField.setErrorEnabled(false);
            nameSettingsTextField.setErrorEnabled(true);


        }


        /*
        email TextField validation
        -------------------------
            email is valid if
                - follows Patterns.EMAIL_ADDRESS
         */
        else if (selectedTextInputField.getId() == emailSettingsTextField.getEditText().getId()) {

            if (!(Patterns.EMAIL_ADDRESS.matcher(s).matches())) {

                emailSettingsTextField.setError("'" + s + "' is not a valid email");
                return;

            }



            emailSettingsTextField.setErrorEnabled(false);
            emailSettingsTextField.setErrorEnabled(true);
        }


        /*
        password TextField validation
        -------------------------
            password is valid if
                - more than 8 char

         */


        else if (selectedTextInputField.getId() == loginPasswordSettingsTextField.getEditText().getId()) {

            if (s.length() < 8) {

                loginPasswordSettingsTextField.setError("password required be 8 character or more*");
                return;

            }

            loginPasswordSettingsTextField.setErrorEnabled(false);
            loginPasswordSettingsTextField.setErrorEnabled(true);
        }

        /*
        phone TextField validation
        -------------------------
            phone is valid if
                - isValidFullNumber() = true

         */
        else if (selectedTextInputField.getId() == phoneSettingsTextField.getEditText().getId()) {

            if (!countrySettingsCodePicker.isValidFullNumber()) {
                phoneSettingsTextField.setError("Phone number is invalid");
                return;

            }


            phoneSettingsTextField.setErrorEnabled(false);
            phoneSettingsTextField.setErrorEnabled(true);
        }
        else if (selectedTextInputField.getId() == phoneSettingsTextField.getEditText().getId()) {
            if (s.length() == 0) {
                phoneSettingsTextField.setErrorEnabled(false);
                phoneSettingsTextField.setErrorEnabled(true);

                return;
            }


            if (!countrySettingsCodePicker.isValidFullNumber()) {

                phoneSettingsTextField.setError("Phone number is invalid");
                return;

            }

            phoneSettingsTextField.setErrorEnabled(false);
            phoneSettingsTextField.setErrorEnabled(true);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            assert data != null;
            imageUri = data.getData();
            imgProfileSettings.setImageURI(imageUri);
            profileImageChanged = true;
            saveButton.setEnabled(isChanged());

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public void pickProfileImage(View view) {
        ImagePicker.Companion.with(this)
                .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                .compress(515)			        //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //getting selected InputField reference to use it in checking validation
        if (hasFocus) {
            selectedTextInputField = (TextInputEditText) v;
        }
    }

    boolean isChanged(){
        return (!(nameSettingsTextField.getEditText().getText().toString().equals(name)
                && emailSettingsTextField.getEditText().getText().toString().equals(email)
                && phoneSettingsTextField.getEditText().getText().toString().equals(phone)
                && !profileImageChanged))
                && checkingAllFieldsValidation();

    }

    boolean checkingAllFieldsValidation() {
        if (nameSettingsTextField.getEditText().getText().length() < 3)
        {
            nameSettingsTextField.setError("Name should be more 2 characters");
            return false;
        }
        if (!(Patterns.EMAIL_ADDRESS.matcher(emailSettingsTextField.getEditText().getText().toString()).matches()))
        {
            emailSettingsTextField.setError("invalid is not a valid email");
            return false;
        }

        if (loginPasswordSettingsTextField.getEditText().getText().length() < 8)
        {
            loginPasswordSettingsTextField.setError("password required be 8 character or more*");
            return false;
        }

        if (!countrySettingsCodePicker.isValidFullNumber())
        {
            phoneSettingsTextField.setError("Phone number is invalid");
            return false;
        }

        return true;
    }

    public void userUpdate(View view) {
        // Check if no view has focus:
        View v = getCurrentFocus();
        if (v != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        saveButton.setEnabled(false);
        setupProfileSettingsProgressWheel.spin();
        setupProfileSettingsProgressTextView.append("updating User Data");

        String password =  loginPasswordSettingsTextField.getEditText().getText().toString();

        Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser user) {
                setupProfileSettingsProgressTextView.append("password is True");

                if(profileImageChanged)
                {
                    settingProfileImg();
                }
                else
                {
                    user.setProperty( "name", nameSettingsTextField.getEditText().getText().toString());
                    user.setProperty( "email", emailSettingsTextField.getEditText().getText().toString());
                    user.setProperty( "phone", phoneSettingsTextField.getEditText().getText().toString());
                    Backendless.UserService.update(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            updateHomeSharedPreference(response);
                            Toast.makeText(ProfileActivity.this, "user Updated", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
                }


            }

            @Override
            public void handleFault(BackendlessFault fault) {
                setupProfileSettingsProgressWheel.stopSpinning();
                setupProfileSettingsProgressTextView.setText("");
                loginPasswordSettingsTextField.setError("wrong password");
                saveButton.setEnabled(true);
            }
        },true);
    }


    File imgFile;


    void settingProfileImg() {
        imgFile = new File(imageUri.getPath());
        setupProfileSettingsProgressTextView.setText("");
        setupProfileSettingsProgressTextView.append("uploading image");

        Backendless.Files.upload(imgFile, "users-Images",true, new AsyncCallback<BackendlessFile>() {
            @Override
            public void handleResponse(BackendlessFile response) {
                Log.i("unlaced done", "handleResponse: "+response.getFileURL());

                setupProfileSettingsProgressTextView.setText("");
                setupProfileSettingsProgressTextView.append("profile picture has been uploaded");

                imageRename(response.getFileURL().substring(response.getFileURL().indexOf("/users-Images/")));

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("not done", "handleResponse: "+fault.toString());


            }
        });


    }


    void imageRename(String oldName) {
        String currentUserId = Backendless.UserService.loggedInUser();
        setupProfileSettingsProgressTextView.setText("");
        setupProfileSettingsProgressTextView.append("setting your Image");
        Toast.makeText(this, "im here", Toast.LENGTH_SHORT).show();
        Backendless.Files.renameFile(oldName, currentUserId+System.currentTimeMillis()+".jpg", new AsyncCallback<String>() {
            @Override
            public void handleResponse(String response) {


                setUserProfileImage(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

    }
    void deleteOldImage() {
        if(profilePic.equals("https://backendlessappcontent.com/30A3F936-C7E6-49FF-8FF6-E4ADF602134B/console/xpklzwwrtkdrxzhxuacjhsmqvgojihawbevk/files/view/users-Images/user.png"))
        {
            return;
        }
        Backendless.Files.remove(path, new AsyncCallback<Integer>() {
            @Override
            public void handleResponse(Integer response) {
                Toast.makeText(ProfileActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    void setUserProfileImage(String url) {
        BackendlessUser user = Backendless.UserService.CurrentUser();
        user.setProperty( "profilePicture", url);
        user.setProperty( "name", nameSettingsTextField.getEditText().getText().toString());
        user.setProperty( "email", emailSettingsTextField.getEditText().getText().toString());
        user.setProperty( "phone", phoneSettingsTextField.getEditText().getText().toString());

        Backendless.UserService.update( user, new AsyncCallback<BackendlessUser>()
        {
            public void handleResponse( BackendlessUser user )
            {
                Toast.makeText(ProfileActivity.this, "image u", Toast.LENGTH_SHORT).show();
                updateHomeSharedPreference(user);
            }

            public void handleFault( BackendlessFault fault )
            {
                // user update failed, to get the error code call fault.getCode()
            }


        });
    }


    void updateHomeSharedPreference(BackendlessUser user) {
        setupProfileSettingsProgressTextView.setText("");
        setupProfileSettingsProgressTextView.append("User info has been updated successfully");

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("updated", user.getProperty("updated").toString());
        editor.putString("userName", user.getProperty("name").toString());
        editor.putString("userEmail", user.getProperty("email").toString());
        editor.putString("userPhone", user.getProperty("phone").toString());
        editor.putInt("userCountryCode", (Integer) user.getProperty("countryCode"));
        editor.putString("userProfilePicture",user.getProperty("profilePicture").toString());
        editor.putBoolean("userProfileUpdated", true);
        editor.apply();
        deleteOldImage();
        onBackPressed();
    }
}