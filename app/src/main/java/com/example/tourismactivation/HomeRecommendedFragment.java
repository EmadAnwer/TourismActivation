package com.example.tourismactivation;

import android.content.Intent;
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
import com.example.tourismactivation.molde.Governorates;
import com.example.tourismactivation.molde.Recommended;
import com.example.tourismactivation.recyclerView.GovernoratesRecyclerViewAdapter;
import com.example.tourismactivation.recyclerView.RecommendedRecyclerViewAdapter;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

public class HomeRecommendedFragment extends Fragment {

    List<Recommended> recommendedList= new ArrayList<>();
    RecyclerView recommendedRecyclerView;
    RecommendedRecyclerViewAdapter adapter;
    DataQueryBuilder queryBuilder;
    int count = -5 ;
    boolean isLoading;

    public HomeRecommendedFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_recommended, container, false);

        //setting governorateRecyclerView
        recommendedRecyclerView = view.findViewById(R.id.recommendedRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        adapter = new RecommendedRecyclerViewAdapter(recommendedList,view.getContext());
        recommendedRecyclerView.setAdapter(adapter);

        Paginate.Callbacks callbacks = new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                if(count == -5)
                    getCount();
                else
                    getMoreRecommended();
            }

            @Override
            public boolean isLoading() {
                // Indicate whether new page loading is in progress or not
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                if(recommendedList == null)
                    return false;

                return count == recommendedList.size() || count == 0;
            }
        };

        Paginate.with(recommendedRecyclerView, callbacks)
                .setLoadingTriggerThreshold(5)
                .addLoadingListItem(true)
                .build();

        return view;
    }

    void getCount()
    {
        isLoading = true;

        Backendless.Data.of( Recommended.class ).getObjectCount(
                new AsyncCallback<Integer>() {
                    @Override
                    public void handleResponse(Integer response) {

                        count = response;
                        if(count == 0)
                        {
                            recommendedList.clear();
                            adapter.notifyDataSetChanged();
                            isLoading = false;
                            return;
                        }
                        getRecommended();
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
    void getRecommended()
    {

        queryBuilder = DataQueryBuilder.create();
        queryBuilder.excludeProperties( "id","governoratePlaces", "created", "updated","ownerId","___class" );
        byte PAGE_SIZE = 10;
        queryBuilder.setPageSize(PAGE_SIZE).setOffset( 0 );

        Backendless.Data.of(Recommended.class).find( queryBuilder,
                new AsyncCallback<List<Recommended>>()
                {
                    @Override
                    public void handleResponse( List<Recommended> response )
                    {
                        recommendedList.addAll(response) ;
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

    void getMoreRecommended()
    {

        isLoading = true;
        queryBuilder.prepareNextPage();
        Backendless.Data.of(Recommended.class).find( queryBuilder,
                new AsyncCallback<List<Recommended>>()
                {
                    @Override
                    public void handleResponse( List<Recommended> response )
                    {
                        recommendedList.addAll(response) ;
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