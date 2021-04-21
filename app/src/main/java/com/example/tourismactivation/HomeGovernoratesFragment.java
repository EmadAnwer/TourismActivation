package com.example.tourismactivation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.tourismactivation.molde.Governorates;
import com.example.tourismactivation.recyclerView.GovernoratesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HomeGovernoratesFragment extends Fragment {


    List<Governorates> governorates= new ArrayList<>();
    RecyclerView governorateRecyclerView;
    GovernoratesRecyclerViewAdapter adapter;


    public HomeGovernoratesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getGovernorates();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_governorates, container, false);
        //setting RecyclerView
        governorateRecyclerView = view.findViewById(R.id.governorateRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        governorateRecyclerView.setLayoutManager(layoutManager);
        adapter = new GovernoratesRecyclerViewAdapter(governorates,view.getContext());
        governorateRecyclerView.setAdapter(adapter);


        return view;
    }


    void getGovernorates()
    {
        Toast.makeText(getContext(), "get Governorates", Toast.LENGTH_SHORT).show();
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.excludeProperties( "id","governoratePlaces", "created", "updated","objectId","ownerId","___class" );
        Backendless.Data.of(Governorates.class).find( queryBuilder,
                new AsyncCallback<List<Governorates>>()
                {
                    @Override
                    public void handleResponse( List<Governorates> response )
                    {

                        governorates.addAll(response) ;
                        adapter.notifyDataSetChanged();

                        // the "response" object is a collection of java.util.Map objects.
                        // each item in the collection represents an object from the "Person" table
                    }

                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        Toast.makeText(getContext(), "error while getting Governorates", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}