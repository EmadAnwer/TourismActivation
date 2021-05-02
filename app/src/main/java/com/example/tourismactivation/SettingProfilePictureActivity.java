package com.example.tourismactivation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.UploadCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.File;
import java.io.IOException;

public class SettingProfilePictureActivity extends AppCompatActivity {
    ShapeableImageView imgProfile;
    BackendlessUser user;
    String oldUrl = "",name = "";
    Uri imageUri;
    boolean hasImage;
    SharedPreferences pref;
    TextView userNameTextView,setupProfileProgressTextView;
    ProgressWheel setupProfileProgressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile_picture);
        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        name = pref.getString("userName", "");

        setupProfileProgressTextView = findViewById(R.id.setupProfileProgressTextView);
        userNameTextView = findViewById(R.id.userNameTextView);
        imgProfile =findViewById(R.id.imgProfile);
        userNameTextView.setText(name);
        setupProfileProgressWheel = findViewById(R.id.setupProfileProgressWheel);
        setupProfileProgressWheel.stopSpinning();

        user = Backendless.UserService.CurrentUser();
    }


    public void pickProfileImage(View view) {
        ImagePicker.Companion.with(this)
                .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                .compress(515)			        //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            assert data != null;
            imageUri = data.getData();
            imgProfile.setImageURI(imageUri);
            hasImage = true;
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public void settingProfileImg(View view) {
        Button b = (Button) view;
        b.setEnabled(false);
        setupProfileProgressWheel.spin();

        if(!hasImage)
        {
            setUserProfileImage("https://backendlessappcontent.com/30A3F936-C7E6-49FF-8FF6-E4ADF602134B/console/xpklzwwrtkdrxzhxuacjhsmqvgojihawbevk/files/view/users-Images/user.png");
            setupProfileProgressTextView.append("setting up your profile info.");
            return;


        }
        setupProfileProgressTextView.append("uploading");
        File imgFile = new File(imageUri.getPath());
        Backendless.Files.upload(imgFile, "users-Images",true, new AsyncCallback<BackendlessFile>() {

            @Override
            public void handleResponse(BackendlessFile response) {

                Log.i("upladed done", "handleResponse: "+response.getFileURL());
                setupProfileProgressTextView.setText("");
                setupProfileProgressTextView.append("profile picture has been uploaded");

                oldUrl= response.getFileURL();
                imageRename("/users-Images/"+imgFile.getName());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("not done", "handleResponse: "+fault.toString());


            }
        });


    }
    void imageRename(String oldName) {
        String currentUserId = Backendless.UserService.loggedInUser();

        Backendless.Files.renameFile(oldName, currentUserId+".jpg", new AsyncCallback<String>() {
            @Override
            public void handleResponse(String response) {
                setupProfileProgressTextView.setText("");
                setupProfileProgressTextView.append("setting up your profile info.");

                Log.i("file rename", "handleResponse: "+response);
                setUserProfileImage(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("error", "handleFault: "+fault.toString());
            }
        });

    }


    void setUserProfileImage(String url) {
        user.setProperty( "profilePicture", url);
        Backendless.UserService.update( user, new AsyncCallback<BackendlessUser>()
        {
            public void handleResponse( BackendlessUser user )
            {
                setupProfileProgressTextView.setText("");
                setupProfileProgressTextView.append("done");

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("userProfilePicture",user.getProperty("profilePicture").toString());
                editor.apply();



                init();
            }

            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(SettingProfilePictureActivity.this, "fault in updating user info", Toast.LENGTH_SHORT).show();
                // user update failed, to get the error code call fault.getCode()
            }


        });
    }
    void init() {



        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        // nullify
        intent = null;

        finish();
    }
}