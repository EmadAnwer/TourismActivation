package com.example.tourismactivation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.example.tourismactivation.molde.Post;
import com.example.tourismactivation.recyclerView.GovernoratesRecyclerViewAdapter;
import com.example.tourismactivation.recyclerView.PostsRecyclerViewAdapter;
import com.example.tourismactivation.services.Posts;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.paginate.Paginate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class HomeExperiencesFragment extends Fragment implements Response.Listener<JSONArray>, Response.ErrorListener, View.OnClickListener, TextWatcher, SwipeRefreshLayout.OnRefreshListener {
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
    Bitmap postImageBitmap;
    ArrayList<Post> posts = new ArrayList<>();
    RecyclerView postsRecyclerView;
    PostsRecyclerViewAdapter adapter;
    SwipeRefreshLayout postsSwipeRefreshLayout;
    TextInputLayout ExperiencePostTextField;
    Uri imageUri;
    ImageView newPostImageView;
    Button postExperienceButton;
    boolean  hasImage,isLoading;
    SharedPreferences pref;
    int count = -5;
    short pageSize = 5;
    public HomeExperiencesFragment() {


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_experiences, container, false);
        newPostImageView = view.findViewById(R.id.newPostImageView);
        ExperiencePostTextField = view.findViewById(R.id.ExperiencePostTextField);
        postsSwipeRefreshLayout = view.findViewById(R.id.postsSwipeRefreshLayout);
        postExperienceButton = view.findViewById(R.id.postExperienceButton);
        postExperienceButton.setOnClickListener(this);
        postsSwipeRefreshLayout.setOnRefreshListener(this);
        queue = Volley.newRequestQueue(this.getContext());
        //setting governorateRecyclerView
        postsRecyclerView = view.findViewById(R.id.postsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        postsRecyclerView.setLayoutManager(layoutManager);
        adapter = new PostsRecyclerViewAdapter(posts,view.getContext());
        postsRecyclerView.setAdapter(adapter);


        ExperiencePostTextField.getEditText().addTextChangedListener(this);

        ExperiencePostTextField.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(HomeExperiencesFragment.this)
                        .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                        .compress(515)			        //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });



        Paginate.Callbacks callbacks = new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                if(count == -5)
                    getCount();
                else
                    nextPageRequest(pageSize);
            }

            @Override
            public boolean isLoading() {
                // Indicate whether new page loading is in progress or not
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                if(posts == null)
                    return false;

                return count == posts.size() || count == 0 ;
            }
        };

        Paginate.with(postsRecyclerView, callbacks)
                .setLoadingTriggerThreshold(10)
                .addLoadingListItem(true)
                .build();
        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            assert data != null;
            imageUri = data.getData();
            newPostImageView.setImageURI(imageUri);
            newPostImageView.setVisibility(View.VISIBLE);
            hasImage = true;

            newPostImageView.invalidate();
            BitmapDrawable drawable = (BitmapDrawable) newPostImageView.getDrawable();
            postImageBitmap = drawable.getBitmap();

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this.getActivity(), getString(R.string.error_task_cancelled) , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.task_cancelled), Toast.LENGTH_SHORT).show();
        }
    }


    void getCount()
    {
        isLoading = true;
        Backendless.Data.of("Experiences").getObjectCount(new AsyncCallback<Integer>() {
            @Override
            public void handleResponse(Integer response) {
                Log.i("posts count :", "c: "+response.toString());
                count =  response;

                if(count ==0)
                {
                    isLoading = false ;
                    return;
                }

                firstPageRequest();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
    void firstPageRequest()
    {

        String url = "https://api.backendless.com/3CD92F0D-FF48-0269-FFEB-9320DA4B8900/37DD6CA7-2A9B-4ABD-BEB7-E5BFA2F0938A/services/Posts/All?currentUserId=" + Backendless.UserService.loggedInUser()+"&pageSize=10&offset=0";
        JsonArrayRequest request = new JsonArrayRequest(url, this, this);
        request.setRetryPolicy(retryPolicy);
        queue.add(request);

    }

    void nextPageRequest(short pageSize)
    {

        String url = "https://api.backendless.com/3CD92F0D-FF48-0269-FFEB-9320DA4B8900/37DD6CA7-2A9B-4ABD-BEB7-E5BFA2F0938A/services/Posts/All?currentUserId=" + Backendless.UserService.loggedInUser()+"&pageSize=+"+pageSize+"&offset="+posts.size();
        JsonArrayRequest request = new JsonArrayRequest(url, this, this);
        request.setRetryPolicy(retryPolicy);
        queue.add(request);

    }


    @Override
    public void onResponse(JSONArray response) {
        Gson gson = new Gson();
        Post[] posts = gson.fromJson(response.toString(),Post[].class);

        this.posts.addAll(Arrays.asList(posts));
        adapter.notifyDataSetChanged();


        isLoading = false;
        postsSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        isLoading = false;

        Log.i("post", "onResponse: "+error.toString());

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.postExperienceButton)
        {
            postExperienceButton.setEnabled(false);
            if(hasImage)
            {
                Backendless.Files.Android.upload(postImageBitmap, Bitmap.CompressFormat.JPEG, 70, Backendless.UserService.loggedInUser()+"-"+System.currentTimeMillis()+".JPEG", "Posts-Images", new AsyncCallback<BackendlessFile>() {
                    @Override
                    public void handleResponse(BackendlessFile response) {
                        Log.i("upladedImage", response.getFileURL());
                        uploadPost(ExperiencePostTextField.getEditText().getText().toString(),response.getFileURL());
                        postExperienceButton.setEnabled(true);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.i("post", fault.toString());
                        postExperienceButton.setEnabled(true);
                    }
                });
            }
            else
                uploadPost(ExperiencePostTextField.getEditText().getText().toString(),null);


        }
    }

    void uploadPost(String content, String image)
    {

        HashMap<String, String> post = new  HashMap<>();
        post.put( "image", image );
        post.put( "content", content );

        Backendless.Data.of("Experiences").save(post, new AsyncCallback<Map>() {
            @Override
            public void handleResponse(Map response) {
                postExperienceButton.setEnabled(true);
                Log.i("post", response.toString());
                addPostToList(response);


            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("post", fault.toString());
                postExperienceButton.setEnabled(true);
            }
        });
    }


    void addPostToList(Map responseMap)
    {
        Post p= new Post();
        pref = getContext().getSharedPreferences("userData", MODE_PRIVATE);
        p.setName(pref.getString("userName", ""));
        p.setUserProfilePicture(pref.getString("userProfilePicture", ""));
        p.setPostId((String) responseMap.get("objectId"));
        p.setContent((String) responseMap.get("content"));
        p.setImage((String) responseMap.get("image"));
        p.setCreated(System.currentTimeMillis());
        p.setTotalLikesCount(0);
        p.setPostedByCurrentUser(true);
        p.setLiked(false);


        posts.add(0,p);
        adapter.notifyDataSetChanged();

        // clean
        newPostImageView.setVisibility(View.GONE);
        hasImage = false;
        ExperiencePostTextField.getEditText().setText(null);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        postExperienceButton.setEnabled(!ExperiencePostTextField.getEditText().getText().toString().isEmpty());

    }

    @Override
    public void onRefresh() {
        posts.clear();
        getCount();
    }
}