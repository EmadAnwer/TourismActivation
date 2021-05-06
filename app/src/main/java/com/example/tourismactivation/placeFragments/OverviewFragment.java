package com.example.tourismactivation.placeFragments;

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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.tourismactivation.R;
import com.example.tourismactivation.molde.Places;
import com.example.tourismactivation.molde.Prices;
import com.example.tourismactivation.molde.Tags;
import com.example.tourismactivation.recyclerView.PricesRecyclerViewAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OverviewFragment extends Fragment {
    TextView rateTextView, reviewsCountTextView, descriptionTextView;
    RecyclerView pricesRecyclerView;
    RatingBar averageRatingBar;
    SharedPreferences pref;
    String id = "";
    Places place;
    ChipGroup placeTagsChipGroup;
    PricesRecyclerViewAdapter adapter;
    List<Prices> pricesList = new ArrayList<>();
    LinearLayoutManager layoutManager;

    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = this.getActivity().getSharedPreferences("placesPref", Context.MODE_PRIVATE);
        id = pref.getString("placeID", "error");


        getPlaceData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        // setting views id's
        rateTextView = view.findViewById(R.id.rateTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        reviewsCountTextView = view.findViewById(R.id.reviewsCountTextView);
        averageRatingBar = view.findViewById(R.id.averageRatingBar);
        placeTagsChipGroup = view.findViewById(R.id.placeTagsChipGroup);
        pricesRecyclerView = view.findViewById(R.id.pricesRecyclerView);

        layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        pricesRecyclerView.setLayoutManager(layoutManager);
        if(adapter == null)
        {
            adapter = new PricesRecyclerViewAdapter(pricesList,view.getContext());

        }
        pricesRecyclerView.setAdapter(adapter);

        //setting views data
        if(place != null) {
            setPlaceDataIntoViews(place);
        }

        return view;
    }

    DataQueryBuilder queryBuilder = DataQueryBuilder.create();

    void getPlaceData() {
        queryBuilder.addRelated("placeTags");
        queryBuilder.addRelated("placePrice");
        queryBuilder.addProperties("reviewsCount", "description", "rating_average");
        Backendless.Data.of(Places.class).findById(id, queryBuilder, new AsyncCallback<Places>() {

            @Override
            public void handleResponse(Places p) {
                place = p;
                for (Prices prices : p.getPlacePrice()) {
                    Log.i("price", "handleResponse: "+prices.getObjectId());
                }
                setPlaceDataIntoViews(place);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    void setPlaceDataIntoViews(Places place){

        rateTextView.setText("");
        rateTextView.append(place.getRating_average().toString());

        reviewsCountTextView.setText("");
        reviewsCountTextView.append("( ");
        reviewsCountTextView.append(place.getReviewsCount().toString());
        reviewsCountTextView.append(" )");

        averageRatingBar.setRating(place.getRating_average());
        descriptionTextView.setText("");
        descriptionTextView.append(place.getDescription());

        addPrices(place.getPlacePrice());
        addTagsChips(place.getPlaceTags());
    }

    void addTagsChips(List<Tags> tagsList)
    {
        Chip chip ;

        for (Tags tag : tagsList) {
            chip = (Chip) getLayoutInflater().inflate(R.layout.tag,placeTagsChipGroup,false);
            chip.setText(tag.getName());
            placeTagsChipGroup.addView(chip);

        }


    }

    void addPrices(List<Prices> pricesList)
    {
        this.pricesList.clear();
        this.pricesList.addAll(pricesList);
        adapter.notifyDataSetChanged();
    }

}