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

import java.util.ArrayList;
import java.util.List;

    public class PlacesActivity extends AppCompatActivity implements View.OnClickListener, ChipGroup.OnCheckedChangeListener {
        RecyclerView placesRecyclerView;
        PlacesRecyclerViewAdapter adapter;
        List<Places> places= new ArrayList<>();
        String coverImage,governorate,filter = "";
        SharedPreferences pref;
        int selectedId;

        // Views
        Toolbar placesToolbar;
        CollapsingToolbarLayout collapsingToolbar;
        ChipGroup placesCategoriesChipGroup;
        ImageView governorateCoverImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        //setting views id's
        placesRecyclerView = findViewById(R.id.placesRecyclerView);
        placesToolbar = findViewById(R.id.placesToolbar);
        placesCategoriesChipGroup = findViewById(R.id.placeTagsChipGroup);
        governorateCoverImageView = findViewById(R.id.governorateCoverImageView);
        collapsingToolbar = findViewById(R.id.collapsingToolbar);


        //getting extras
        pref = getSharedPreferences("governoratePref", Context.MODE_PRIVATE);
        coverImage = pref.getString("coverImage","error");
        governorate = pref.getString("governorate","error");

        Toast.makeText(this, governorate, Toast.LENGTH_SHORT).show();

        //setting cover and governorate name
        Glide.with(this)
                .asBitmap()
                .load(coverImage)
                .into(governorateCoverImageView);
        collapsingToolbar.setTitle(governorate);

        addPlaces();



        //setting Categories Group Checked Listener
        placesCategoriesChipGroup.setOnCheckedChangeListener(this);
        selectedId = placesCategoriesChipGroup.getCheckedChipId();

                //setting toolbar -> back
        placesToolbar.setNavigationOnClickListener(this);
        //setting RecyclerView
        placesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        placesRecyclerView.setLayoutManager(layoutManager);
        adapter = new PlacesRecyclerViewAdapter(places,this);
        placesRecyclerView.setAdapter(adapter);

    }


    void addPlaces()
    {
        Toast.makeText(this, "get Governorates", Toast.LENGTH_SHORT).show();
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.excludeProperties( "id","category", "description","governorate","placeImage","created", "updated","objectId","ownerId","user_id","placeTags","placeReviews","placePrice","placeImage");

        String whereClause = "governorate='"+governorate+"'"+filter;

        queryBuilder.setWhereClause(whereClause);
        Backendless.Data.of( Places.class ).find( queryBuilder,
                new AsyncCallback<List<Places>>()
                {
                    @Override
                    public void handleResponse(List<Places> response )
                    {
                        Toast.makeText(PlacesActivity.this, ""+whereClause, Toast.LENGTH_SHORT).show();
                        places.addAll(response);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        Log.i("places Error", "handleFault: "+fault.getCode());
                        if(fault.getCode().equals("Internal client exception"))
                            Toast.makeText(PlacesActivity.this, "internet error", Toast.LENGTH_SHORT).show();
                        else if(fault.getCode().equals("3064"))
                        {
                            Toast.makeText(PlacesActivity.this, "user error", Toast.LENGTH_SHORT).show();

                        }

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
                Toast.makeText(this, ""+checkedId, Toast.LENGTH_SHORT).show();
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

                Toast.makeText(this, "allCategoriesChip", Toast.LENGTH_SHORT).show();
            }
            else if(checkedId == R.id.historicalChip)
            {
                filter =" and category ='Historical'";
                Toast.makeText(this, "historicalChip", Toast.LENGTH_SHORT).show();

            }
            else if(checkedId == R.id.museumChip)
            {
                filter =" and category ='Museum'";

                Toast.makeText(this, "museumChip", Toast.LENGTH_SHORT).show();

            }
            else if(checkedId == R.id.naturalChip)
            {
                filter =" and category ='Natural'";

                Toast.makeText(this, "naturalChip", Toast.LENGTH_SHORT).show();

            }
            else if(checkedId == R.id.restaurantsChip)
            {
                filter =" and category ='Restaurants'";

                Toast.makeText(this, "restaurantsChip", Toast.LENGTH_SHORT).show();

            }
            else if(checkedId == R.id.localChip)
            {
                filter =" and category ='Local'";

                Toast.makeText(this, "localChip", Toast.LENGTH_SHORT).show();

            }
            else if(checkedId == R.id.shoppingChip)
            {
                filter =" and category ='Shopping'";

                Toast.makeText(this, "shoppingChip", Toast.LENGTH_SHORT).show();

            }
            places.clear();
            adapter.notifyDataSetChanged();
            addPlaces();




        }








    }