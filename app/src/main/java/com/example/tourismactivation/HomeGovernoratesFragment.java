package com.example.tourismactivation;

import android.content.Intent;
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
import com.example.tourismactivation.molde.Places;
import com.example.tourismactivation.recyclerView.GovernoratesRecyclerViewAdapter;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HomeGovernoratesFragment extends Fragment {


    List<Governorates> governorates= new ArrayList<>();
    RecyclerView governorateRecyclerView;
    GovernoratesRecyclerViewAdapter adapter;
    DataQueryBuilder queryBuilder;
    int count = -5 ;
    boolean isLoading;

    public HomeGovernoratesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_governorates, container, false);

        //setting governorateRecyclerView
        governorateRecyclerView = view.findViewById(R.id.governorateRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        governorateRecyclerView.setLayoutManager(layoutManager);
        adapter = new GovernoratesRecyclerViewAdapter(governorates,view.getContext());
        governorateRecyclerView.setAdapter(adapter);

        Paginate.Callbacks callbacks = new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                if(count == -5)
                    getCount();
                else
                    getMoreGovernorates();
            }

            @Override
            public boolean isLoading() {
                // Indicate whether new page loading is in progress or not
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                if(governorates == null)
                    return false;

                return count == governorates.size() || count == 0;
            }
        };

        Paginate.with(governorateRecyclerView, callbacks)
                .setLoadingTriggerThreshold(10)
                .addLoadingListItem(true)
                .build();

        return view;
    }


    void getCount()
    {
        isLoading = true;

        Backendless.Data.of( Governorates.class ).getObjectCount(
                new AsyncCallback<Integer>() {
                    @Override
                    public void handleResponse(Integer response) {

                        count = response;
                        if(count == 0)
                        {
                            governorates.clear();
                            adapter.notifyDataSetChanged();
                            isLoading = false;
                            return;
                        }
                        getGovernorates();
                        Log.i("Places count", "handleResponse: "+response);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.i("Places count", "handleResponse: "+fault.toString());
                        {
                            if(fault.getCode().equals("3064") )
                            {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                            else if(fault.getCode().equals("3064") )
                            {

                            }

                            else {
                                Log.i("error", "handleFault: "+fault);
                            }

                            Log.i("govFrag Error", "handleFault: "+fault.getCode());

                        }
                    }
                });

    }



    void getMoreGovernorates()
    {
        isLoading = true;

        queryBuilder.prepareNextPage();
        Backendless.Data.of(Governorates.class).find( queryBuilder,
                new AsyncCallback<List<Governorates>>()
                {
                    @Override
                    public void handleResponse( List<Governorates> response )
                    {
                        governorates.addAll(response) ;
                        adapter.notifyDataSetChanged();
                        isLoading = false;

                    }

                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        if(fault.getCode().equals("3064") )
                        {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else if(fault.getCode().equals("3064") )
                        {

                        }

                        else {
                            Log.i("error", "handleFault: "+fault);
                        }

                        Log.i("govFrag Error", "handleFault: "+fault.getCode());

                    }
                });






    }
    void getGovernorates()
    {
        queryBuilder = DataQueryBuilder.create();
        queryBuilder.excludeProperties( "id","governoratePlaces", "created", "updated","ownerId","___class" );
        byte PAGE_SIZE = 10;
        queryBuilder.setPageSize(PAGE_SIZE).setOffset( 0 );

        Backendless.Data.of(Governorates.class).find( queryBuilder,
                new AsyncCallback<List<Governorates>>()
                {
                    @Override
                    public void handleResponse( List<Governorates> response )
                    {
                        governorates.addAll(response) ;
                        adapter.notifyDataSetChanged();
                        isLoading = false;

                    }

                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        if(fault.getCode().equals("3064") )
                        {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else if(fault.getCode().equals("3064") )
                        {

                        }

                        else {
                            Log.i("error", "handleFault: "+fault);
                        }

                        Log.i("govFrag Error", "handleFault: "+fault.getCode());

                    }
                });


    }
}