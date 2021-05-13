package com.example.tourismactivation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.tourismactivation.molde.Places;
import com.example.tourismactivation.recyclerView.PlacesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class RecommendedPlacesFragment extends Fragment {
    List<Places> places = new ArrayList<>();
    DataQueryBuilder queryBuilder;
    String recommendedObjectId;
    SharedPreferences pref;
    RecyclerView recommendedRecyclerView;
    PlacesRecyclerViewAdapter adapter;


    public RecommendedPlacesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPlaces();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommended_places, container, false);
        recommendedRecyclerView = view.findViewById(R.id.recommendedRecyclerView);

        //setting RecyclerView
        recommendedRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        adapter = new PlacesRecyclerViewAdapter(places,this.getContext());
        recommendedRecyclerView.setAdapter(adapter);



        return view;
    }


    void getPlaces()
    {
        pref = this.getActivity().getSharedPreferences("recommendedPref", Context.MODE_PRIVATE);
        recommendedObjectId = pref.getString("recommendedObjectId","error");

        queryBuilder = DataQueryBuilder.create();
        queryBuilder.excludeProperties( "id","category", "description","governorate","placeImage","created", "updated","ownerId","user_id","placeTags","placeReviews","placePrice","placeImage");
        String whereClause = "Recommended[recommendedPlaces].objectId= '"+recommendedObjectId+"'";
        Log.i("TAG", "getFirstPlaces: "+whereClause);
        queryBuilder.setWhereClause(whereClause);
        byte PAGE_SIZE = 20;
        queryBuilder.setPageSize(PAGE_SIZE).setOffset( 0 );


        Backendless.Data.of( Places.class ).find(queryBuilder,
                new AsyncCallback<List<Places>>()
                {
                    @Override
                    public void handleResponse(List<Places> response )
                    {
                        Log.i("palces", "handleResponse: "+response.size());
                        places.addAll(response);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        Log.i("places Error", "handleFault: "+fault.getCode());


                    }
                });

    }

}