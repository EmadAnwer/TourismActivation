package com.example.tourismactivation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.backendless.Backendless;
import com.example.tourismactivation.molde.Post;
import com.example.tourismactivation.recyclerView.GovernoratesRecyclerViewAdapter;
import com.example.tourismactivation.recyclerView.PostsRecyclerViewAdapter;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class HomeExperiencesFragment extends Fragment implements Response.Listener<JSONArray>, Response.ErrorListener, View.OnClickListener {
    RetryPolicy retryPolicy = new RetryPolicy() {
        @Override
        public int getCurrentTimeout() {
            return 50000;
        }

        @Override
        public int getCurrentRetryCount() {
            return 50000;
        }

        @Override
        public void retry(VolleyError error) throws VolleyError {
            Log.i("Post error", "onResponse: "+error.toString());
        }
    };
    RequestQueue queue;
    ArrayList<Post> posts = new ArrayList<>();
    RecyclerView postsRecyclerView;
    PostsRecyclerViewAdapter adapter;
    TextInputLayout ExperiencePostTextField;
    Uri imageUri;
    ImageView newPostImageView;
    boolean  hasImage;
    public HomeExperiencesFragment() {


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StringBuilder line = new StringBuilder();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_experiences, container, false);
        newPostImageView = view.findViewById(R.id.newPostImageView);
        ExperiencePostTextField = view.findViewById(R.id.ExperiencePostTextField);

        queue = Volley.newRequestQueue(this.getContext());

        firstPageRequest();


        //setting governorateRecyclerView
        postsRecyclerView = view.findViewById(R.id.postsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        postsRecyclerView.setLayoutManager(layoutManager);
        adapter = new PostsRecyclerViewAdapter(posts,view.getContext());
        postsRecyclerView.setAdapter(adapter);


        ExperiencePostTextField.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(getActivity())
                        .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                        .compress(515)			        //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        return view;
    }


    /*
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        Toast.makeText(getActivity(), "Im hjere", Toast.LENGTH_SHORT).show();

        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            assert data != null;
            imageUri = data.getData();
            newPostImageView.setImageURI(imageUri);
            newPostImageView.setVisibility(View.VISIBLE);
            hasImage = true;

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this.getActivity(), getString(R.string.error_task_cancelled) , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.task_cancelled), Toast.LENGTH_SHORT).show();
        }
    }
*/

    void firstPageRequest()
    {

        String url = "https://api.backendless.com/31908FE0-A688-43D5-879D-B815B9404108/2025C2E4-180B-492E-9BC0-2916C99A2851/services/Posts/All?currentUserId=" + Backendless.UserService.loggedInUser()+"&pageSize=10&offset=0";
        JsonArrayRequest request = new JsonArrayRequest(url, this, this);
        request.setRetryPolicy(retryPolicy);
        queue.add(request);

    }


    @Override
    public void onResponse(JSONArray response) {
        Log.i("post", "onResponse: "+response.toString());

        Gson gson = new Gson();
        Post[] posts = gson.fromJson(response.toString(),Post[].class);

        this.posts.addAll(Arrays.asList(posts));
        adapter.notifyDataSetChanged();

        for (Post post : this.posts) {
            Log.i("post", "onResponse: "+post.getContent());
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i("post", "onResponse: "+error.toString());

    }

    @Override
    public void onClick(View v) {
    }
}