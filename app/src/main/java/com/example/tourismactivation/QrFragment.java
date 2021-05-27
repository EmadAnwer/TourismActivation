package com.example.tourismactivation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.tourismactivation.molde.Places;
import com.google.zxing.Result;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.Context.MODE_PRIVATE;


public class QrFragment extends Fragment implements DecodeCallback, View.OnClickListener {
    int processors = Runtime.getRuntime().availableProcessors();
    ExecutorService pool = Executors.newFixedThreadPool(processors);
    SharedPreferences pref;
    private static final int RESULT_OK = 1;
    private CodeScanner mCodeScanner;
    CodeScannerView scannerView;
    Button button2;
    boolean firstTime = true,DENIED;

    // but unique for each permission.
    private static final int CAMERA_PERMISSION_CODE = 11;

    public QrFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pref = getContext().getSharedPreferences("Permission", MODE_PRIVATE);
        firstTime = pref.getBoolean("camera",false);


        final Activity activity = getActivity();
        View root = inflater.inflate(R.layout.fragment_qr, container, false);
        scannerView = root.findViewById(R.id.scanner_view);
        button2 =root.findViewById(R.id.button2);
        button2.setOnClickListener(this);
        checkPermission();
        assert activity != null;

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mCodeScanner != null)
            mCodeScanner.startPreview();

        if(pref.getBoolean("eUpdated",false))
        {
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("eUpdated",false);
            editor.apply();
            int permission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

            if (permission == PackageManager.PERMISSION_GRANTED)
            {
                getActivity().finish();
                startActivity(this.getActivity().getIntent());
            }



        }

    }

    @Override
    public void onPause() {
        if(mCodeScanner != null)
            mCodeScanner.releaseResources();
        super.onPause();
    }






    void checkPermission()
    {
        final Activity activity = getActivity();

        assert activity != null;
        int permission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED && !firstTime) {
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("camera",true);
            editor.apply();
            makeRequest();
        }
        else if (permission == PackageManager.PERMISSION_DENIED)
        {
            scannerView.setVisibility(View.GONE);
            button2.setVisibility(View.VISIBLE);
            DENIED = true;

        }
        else {
            mCodeScanner = new CodeScanner(activity, scannerView);
            mCodeScanner.setDecodeCallback(this);
        }
    }


    void makeRequest()
    {
        final Activity activity = getActivity();
        assert activity != null;
        requestPermissions(new String[] {Manifest.permission.CAMERA },CAMERA_PERMISSION_CODE);

    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        final Activity activity = getActivity();


        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            }

        }


        }


    @Override
    public void onDecoded(@NonNull @NotNull Result result) {
        final Activity activity = getActivity();

        assert activity != null;
        activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Backendless.Data.of(Places.class).findById(result.getText(), new AsyncCallback<Places>() {
                            @Override
                            public void handleResponse(Places p) {
                                pref = getContext().getSharedPreferences("placesPref", MODE_PRIVATE);
                                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
                                editor.putString("coverImage", p.getCover_image());
                                editor.putString("placeName", p.getName());
                                editor.putString("placeID", p.getObjectId());
                                editor.apply();
                                // intent to PlacesActivity
                                Intent intent = new Intent(getContext(), PlaceActivity.class);
                                getContext().startActivity(intent);
                                intent = null;
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(activity, getString(R.string.WrongQR), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });
            }

    @Override
    public void onClick(View v) {
        if(DENIED)
        {
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("eUpdated",true);
            editor.apply();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",getContext().getPackageName(), null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
        else
            makeRequest();
    }
}