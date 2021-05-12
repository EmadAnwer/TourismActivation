package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.tourismactivation.molde.Places;
import com.example.tourismactivation.molde.Users;
import com.example.tourismactivation.recyclerView.PlacesRecyclerViewAdapter;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences pref;
    RecyclerView favoritesRecyclerView;
    TextView noFavoritesTextView;
    Toolbar favoritesToolbar;
    int count = -5;
    PlacesRecyclerViewAdapter adapter;
    List<Places> favoritePlaces = new ArrayList<>();
    boolean isLoading;
    String userID;
    DataQueryBuilder queryBuilder = DataQueryBuilder.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        userID = Backendless.UserService.loggedInUser();
        pref = getSharedPreferences("placesPref", Context.MODE_PRIVATE);
        favoritesRecyclerView =findViewById(R.id.favoritesRecyclerView);
        noFavoritesTextView =findViewById(R.id.noFavoritesTextView);
        favoritesToolbar =findViewById(R.id.favoritesToolbar);
        favoritesToolbar.setNavigationOnClickListener(this);
        //QB
        queryBuilder.setWhereClause("Users[userFavorites].objectId= '"+userID+"'");


        favoritesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        favoritesRecyclerView.setLayoutManager(layoutManager);
        adapter = new PlacesRecyclerViewAdapter(favoritePlaces,this);
        favoritesRecyclerView.setAdapter(adapter);


        Paginate.Callbacks callbacks = new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                if(count == -5)
                    getCount();
                else
                    getNextFavoritesPlaces();
            }

            @Override
            public boolean isLoading() {
                // Indicate whether new page loading is in progress or not
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                if(favoritePlaces == null)
                    return false;

                return count == favoritePlaces.size() || count == 0;
            }
        };

        Paginate.with(favoritesRecyclerView, callbacks)
                .setLoadingTriggerThreshold(10)
                .addLoadingListItem(true)
                .build();


    }



    void getCount()
    {

        isLoading = true;
        Backendless.Data.of(Places.class).getObjectCount(queryBuilder, new AsyncCallback<Integer>() {
            @Override
            public void handleResponse(Integer response) {
                count = response;
                Log.i("FavoritesActivity", "count " + response);
                if(count == 0)
                {
                    favoritePlaces.clear();
                    adapter.notifyDataSetChanged();
                    isLoading = false;
                    return;
                }
                getFavoritesPlaces();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }


    void getFavoritesPlaces()
    {

        queryBuilder.setPageSize(10).setOffset( 0 );

        Backendless.Data.of(Places.class).find(queryBuilder, new AsyncCallback<List<Places>>() {
            @Override
            public void handleResponse(List<Places> response) {
                favoritePlaces.clear();
                favoritePlaces.addAll(response);
                adapter.notifyDataSetChanged();
                isLoading = false;
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

    }


    void getNextFavoritesPlaces()
    {
        isLoading = true;
        queryBuilder.prepareNextPage();

        Backendless.Data.of(Places.class).find(queryBuilder, new AsyncCallback<List<Places>>() {
            @Override
            public void handleResponse(List<Places> response) {
                favoritePlaces.addAll(response);
                adapter.notifyDataSetChanged();
                isLoading = false;
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("places Error", "handleFault: "+fault.getCode());
                if(fault.getCode().equals("Internal client exception"))
                    Toast.makeText(FavoritesActivity.this, getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                else if(fault.getCode().equals("3064"))
                {
                    Toast.makeText(FavoritesActivity.this, getString(R.string.user_error), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }





    @Override
    public void onClick(View v) {

        finish();
        onBackPressed();
    }
}