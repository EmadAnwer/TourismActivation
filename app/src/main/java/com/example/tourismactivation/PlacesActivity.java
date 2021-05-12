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
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.bumptech.glide.Glide;
import com.example.tourismactivation.molde.Places;
import com.example.tourismactivation.recyclerView.PlacesRecyclerViewAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

    public class PlacesActivity extends AppCompatActivity implements View.OnClickListener, ChipGroup.OnCheckedChangeListener {
        RecyclerView placesRecyclerView;
        PlacesRecyclerViewAdapter adapter;
        DataQueryBuilder queryBuilder;
        List<Places> places;
        String coverImage,governorate,filter = "",governorateObjectId;
        SharedPreferences pref;
        int selectedId;
        boolean isLoading ,isLastPage = false;
        // Views
        Toolbar placesToolbar;
        CollapsingToolbarLayout collapsingToolbar;
        ChipGroup placesCategoriesChipGroup;
        ImageView governorateCoverImageView;
        int count = -5 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        places = new ArrayList<>();
        //setting views id's
        placesRecyclerView = findViewById(R.id.placesRecyclerView);

        placesToolbar = findViewById(R.id.ticketsToolbar);
        placesCategoriesChipGroup = findViewById(R.id.ticketsFilterChipGroup);
        governorateCoverImageView = findViewById(R.id.governorateCoverImageView);
        collapsingToolbar = findViewById(R.id.ticketsCollapsingToolbar);

        //getting extras
        pref = getSharedPreferences("governoratePref", Context.MODE_PRIVATE);
        coverImage = pref.getString("coverImage", "error");
        governorate = pref.getString("governorate", "error");
        governorateObjectId = pref.getString("governorateObjectId", "error");
        Log.i("TAG", "governorateObjectId: "+governorateObjectId);

        Toast.makeText(this, governorate, Toast.LENGTH_SHORT).show();

        //setting cover and governorate name
        Glide.with(this)
                .asBitmap()
                .load(coverImage)
                .into(governorateCoverImageView);
        collapsingToolbar.setTitle(governorate);
        //setting Categories Group Checked Listener
        placesCategoriesChipGroup.setOnCheckedChangeListener(this);
        selectedId = placesCategoriesChipGroup.getCheckedChipId();

        //setting toolbar -> back
        placesToolbar.setNavigationOnClickListener(this);
        //setting RecyclerView
        placesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        placesRecyclerView.setLayoutManager(layoutManager);
        adapter = new PlacesRecyclerViewAdapter(places, this);
        placesRecyclerView.setAdapter(adapter);
        Paginate.Callbacks callbacks = new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                if(count == -5)
                    getCount();
                else
                    getMorePlaces();
            }

            @Override
            public boolean isLoading() {
                // Indicate whether new page loading is in progress or not
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                if(places == null)
                    return false;

                return count == places.size() || count == 0;
            }
        };

        Paginate.with(placesRecyclerView, callbacks)
                .setLoadingTriggerThreshold(10)
                .addLoadingListItem(true)
                .build();

        /*placesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                                   @Override
                                                   public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                                                       super.onScrollStateChanged(recyclerView, newState);


                                                       int visibleItemCount = layoutManager.getChildCount();
                                                       int totalItemCount = layoutManager.getItemCount();
                                                       int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                                                       if (!recyclerView.canScrollVertically(1) && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                                               && firstVisibleItemPosition >= 0
                                                               && totalItemCount >= PAGE_SIZE) {
                                                           Toast.makeText(PlacesActivity.this, "end", Toast.LENGTH_SHORT).show();

                                                       }
                                                   }
                                               });
*/
        /*
        placesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();


                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE)
                    {
                        isLoading = true;
                        getMorePlaces();
                    }


                }
            }
        });

         */
    }



        void getMorePlaces()
    {
        isLoading = true;
        queryBuilder.prepareNextPage();
        Backendless.Data.of( Places.class ).find(queryBuilder,
                new AsyncCallback<List<Places>>()
                {
                    @Override
                    public void handleResponse(List<Places> response )
                    {
                        places.addAll(response);
                        adapter.notifyDataSetChanged();
                        isLoading = false;
                    }

                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        Log.i("places Error", "handleFault: "+fault.getCode());
                        if(fault.getCode().equals("Internal client exception"))
                            Toast.makeText(PlacesActivity.this, getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                        else if(fault.getCode().equals("3064"))
                        {
                            Toast.makeText(PlacesActivity.this, getString(R.string.user_error), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }




    void getFirstPlaces()
    {
        queryBuilder = DataQueryBuilder.create();
        queryBuilder.excludeProperties( "id","category", "description","governorate","placeImage","created", "updated","ownerId","user_id","placeTags","placeReviews","placePrice","placeImage");
        String whereClause = "Governorates[governoratePlaces].objectId= '"+governorateObjectId+"'"+filter;
        Log.i("TAG", "getFirstPlaces: "+whereClause);
        queryBuilder.setWhereClause(whereClause);
        byte PAGE_SIZE = 10;
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

                        isLoading = false;
                    }

                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        Log.i("places Error", "handleFault: "+fault.getCode());
                        if(fault.getCode().equals("Internal client exception"))
                            Toast.makeText(PlacesActivity.this, getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                        else if(fault.getCode().equals("3064"))
                        {
                            Toast.makeText(PlacesActivity.this, getString(R.string.user_error), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

    void  getCount()
    {
        isLoading = true;
        DataQueryBuilder  qb = DataQueryBuilder.create();
        String whereClause = "Governorates[governoratePlaces].objectId= '"+governorateObjectId+"'"+filter;
        qb.setWhereClause(whereClause);
        Backendless.Data.of( Places.class ).getObjectCount(
                qb,new AsyncCallback<Integer>() {
                    @Override
                    public void handleResponse(Integer response) {

                        count = response;
                        if(count == 0)
                        {
                            places.clear();
                            adapter.notifyDataSetChanged();
                            isLoading = false;
                            return;
                        }
                        getFirstPlaces();
                        Log.i("Places count", "handleResponse: "+response);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.i("Places count", "handleResponse: "+fault.toString());

                    }
                });
    }


        @Override
        public void onClick(View v) {
            //clear and nullify
            places.clear();
            places = null;
            coverImage = null ;
            governorate = null;
            filter = null;

            finish();
            onBackPressed();
        }


        @Override
        public void onCheckedChanged(ChipGroup group, int checkedId) {
            /*if the same item chip unchecked
             recheck it again
             return
             */
        if(checkedId == -1)
            {
                Chip c = findViewById(selectedId);
                c.setChecked(true);
                c = null;
                return;
            }

            //if the same item chip checked do nothing
            if(selectedId == checkedId)
                return;

        selectedId = checkedId;
        if(checkedId == R.id.allCategoriesChip)
            {


                filter = "";


            }
            else if(checkedId == R.id.historicalChip)
            {
                filter =" and category ='Historical'";

            }
            else if(checkedId == R.id.museumChip)
            {
                filter =" and category ='Museum'";


            }
            else if(checkedId == R.id.naturalChip)
            {
                filter =" and category ='Natural'";


            }
            else if(checkedId == R.id.restaurantsChip)
            {
                filter =" and category ='Restaurants'";


            }
            else if(checkedId == R.id.localChip)
            {
                filter =" and category ='Local'";


            }
            else if(checkedId == R.id.shoppingChip)
            {
                filter =" and category ='Shopping'";

            }
            count= -5;
            places.clear();
            adapter.notifyDataSetChanged();




        }








    }