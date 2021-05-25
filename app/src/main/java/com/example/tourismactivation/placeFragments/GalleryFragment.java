package com.example.tourismactivation.placeFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.backendless.persistence.DataQueryBuilder;
import com.example.tourismactivation.R;
import com.example.tourismactivation.molde.Images;
import com.example.tourismactivation.molde.Places;
import com.example.tourismactivation.recyclerView.GovernoratesRecyclerViewAdapter;
import com.example.tourismactivation.recyclerView.ImagesRecyclerViewAdapter;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment {
    Uri imageUri;
    ImageView holderImageView;
    RecyclerView imagesRecyclerView ;
    DataQueryBuilder queryBuilder = DataQueryBuilder.create();
    ImagesRecyclerViewAdapter adapter;
    List<Images> images = new ArrayList<>();
    SharedPreferences pref;
    String id;

    public GalleryFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getImage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        imagesRecyclerView = view.findViewById(R.id.imagesRecyclerView);
        holderImageView = view.findViewById(R.id.holderImageView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),2,LinearLayoutManager.VERTICAL,false);
        imagesRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ImagesRecyclerViewAdapter(images,view.getContext(),this);
        imagesRecyclerView.setAdapter(adapter);



        return view;
    }

    void getImage()
    {
        pref = getActivity().getSharedPreferences("placesPref", Context.MODE_PRIVATE);
        id = pref.getString("placeID", "error");
        queryBuilder.setPageSize(100);
        queryBuilder.setWhereClause("Places[placeImage].objectId= '"+id+"'");

        Backendless.Data.of(Images.class).find(queryBuilder, new AsyncCallback<List<Images>>() {
            @Override
            public void handleResponse(List<Images> response) {


                images.addAll(response);
                if(images.size() == 0)
                    images.add(null);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            assert data != null;
            imageUri = data.getData();
            holderImageView.setImageURI(imageUri);
            BitmapDrawable drawable = (BitmapDrawable) holderImageView.getDrawable();
            Bitmap postImageBitmap = drawable.getBitmap();

            Backendless.Files.Android.upload(postImageBitmap, Bitmap.CompressFormat.JPEG, 70, Backendless.UserService.loggedInUser() + "-" + System.currentTimeMillis() + ".JPEG", "Places_Images", new AsyncCallback<BackendlessFile>() {
                @Override
                public void handleResponse(BackendlessFile response) {

                    pref = getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);

                    Images im = new Images();
                    im.setSrc(response.getFileURL());
                    im.setUser_name(pref.getString("userName",""));

                    Backendless.Data.of(Images.class).save(im, new AsyncCallback<Images>() {
                        @Override
                        public void handleResponse(Images response) {
                            images.add(1,response);


                            Backendless.Data.of(Places.class).addRelation(id, "placeImage:Images:n","objectId = '"+response.getObjectId()+"'" , new AsyncCallback<Integer>() {
                                @Override
                                public void handleResponse(Integer response) {
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Log.i("TAG", "handleFault: "+fault.toString());
                                }
                            });
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this.getActivity(), getString(R.string.error_task_cancelled) , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.task_cancelled), Toast.LENGTH_SHORT).show();
        }
    }
}