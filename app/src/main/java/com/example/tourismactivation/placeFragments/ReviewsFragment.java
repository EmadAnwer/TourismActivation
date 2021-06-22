package com.example.tourismactivation.placeFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.tourismactivation.R;
import com.example.tourismactivation.molde.Places;
import com.example.tourismactivation.molde.Post;
import com.example.tourismactivation.molde.Review;
import com.example.tourismactivation.molde.Reviews;
import com.example.tourismactivation.recyclerView.PostsRecyclerViewAdapter;
import com.example.tourismactivation.recyclerView.ReviewsRecyclerViewAdapter;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ReviewsFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONArray>, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

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
    RecyclerView reviewsRecyclerView;
    SwipeRefreshLayout reviewsSwipeRefreshLayout;
    TextInputLayout reviewsPostTextField;
    Button reviewsButton;
    RatingBar reviewsRatingBar;
    RequestQueue queue;
    ReviewsRecyclerViewAdapter adapter;
    SharedPreferences pref;
    String id;
    List<Reviews> reviews = new ArrayList<>();
    public ReviewsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        reviewsButton = view.findViewById(R.id.reviewsButton);
        reviewsRatingBar = view.findViewById(R.id.reviewsRatingBar);
        reviewsRecyclerView = view.findViewById(R.id.reviewsRecyclerView);
        reviewsPostTextField = view.findViewById(R.id.reviewsPostTextField);
        reviewsSwipeRefreshLayout = view.findViewById(R.id.reviewsSwipeRefreshLayout);
        reviewsSwipeRefreshLayout.setOnRefreshListener(this);
        reviewsButton.setOnClickListener(this);
        queue = Volley.newRequestQueue(this.getContext());
        //setting governorateRecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        adapter = new ReviewsRecyclerViewAdapter(reviews,view.getContext());
        reviewsRecyclerView.setAdapter(adapter);

        if(reviews.size() == 0)
            firstPageRequest();

        return view;
    }


    void firstPageRequest()
    {
        pref = getActivity().getSharedPreferences("placesPref", Context.MODE_PRIVATE);
        id = pref.getString("placeID", "error");

        String url = "https://api.backendless.com/3CD92F0D-FF48-0269-FFEB-9320DA4B8900/37DD6CA7-2A9B-4ABD-BEB7-E5BFA2F0938A/services/Review/All?currentUserId="+ Backendless.UserService.loggedInUser()+"&pageSize=100&offset=0&placeID="+id;
        JsonArrayRequest request = new JsonArrayRequest(url, this, this);
        request.setRetryPolicy(retryPolicy);
        queue.add(request);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONArray response) {
        Gson gson = new Gson();
        Reviews[] reviews = gson.fromJson(response.toString(),Reviews[].class);
        this.reviews.addAll(Arrays.asList(reviews));
        adapter.notifyDataSetChanged();
        reviewsSwipeRefreshLayout.setRefreshing(false);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.reviewsButton)
        {
            reviewsButton.setEnabled(false);
            if(!reviewsPostTextField.getEditText().getText().toString().isEmpty())
            {



                HashMap<String, Object> map = new HashMap<>();
                map.put("content",reviewsPostTextField.getEditText().getText().toString());
                map.put("rate",reviewsRatingBar.getRating());


                Backendless.Data.of("Reviews").save(map, new AsyncCallback<Map>() {
                    @Override
                    public void handleResponse(Map response) {
                        Log.i("reviews", "handleResponse: " + response.toString());

                        Backendless.Data.of(Places.class).addRelation(id, "placeReviews:Reviews:n", "objectId = '" + response.get("objectId") + "'", new AsyncCallback<Integer>() {
                            @Override
                            public void handleResponse(Integer response) {


                                Reviews r = new Reviews();
                                r.setTotalUsefulsCount(0);
                                r.setPostedByCurrentUser(true);
                                r.setUsefuled(false);
                                r.setPostId(id);
                                r.setContent(reviewsPostTextField.getEditText().getText().toString());
                                r.setRate(reviewsRatingBar.getRating());
                                r.setCreated(System.currentTimeMillis());
                                pref = getContext().getSharedPreferences("userData", MODE_PRIVATE);
                                r.setName(pref.getString("userName", ""));
                                r.setUserProfilePicture(pref.getString("userProfilePicture", ""));

                                reviews.add(0,r);
                                adapter.notifyDataSetChanged();
                                reviewsPostTextField.getEditText().setText("");
                                reviewsRatingBar.setRating(0);
                                reviewsButton.setEnabled(true);

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
            }
        }
    }

    @Override
    public void onRefresh() {
        reviews.clear();
        firstPageRequest();
    }
}