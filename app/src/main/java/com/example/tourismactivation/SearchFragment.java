package com.example.tourismactivation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.tourismactivation.molde.Governorates;
import com.example.tourismactivation.molde.Tags;
import com.example.tourismactivation.recyclerView.GovernoratesRecyclerViewAdapter;
import com.example.tourismactivation.recyclerView.GovernoratesSearchRecyclerViewAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements View.OnClickListener {
    RecyclerView searchGovernoratesRecyclerView;
    ChipGroup searchTagChipGroup,searchCategoryChipGroup;
    GovernoratesSearchRecyclerViewAdapter adapter;
    Button searchButton;
    List<Governorates> governorates= new ArrayList<>();
    SharedPreferences pref;
    Chip chip;
    DataQueryBuilder  queryBuilder = DataQueryBuilder.create();



    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = this.getActivity().getSharedPreferences("search", Context.MODE_PRIVATE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchTagChipGroup = view.findViewById(R.id.searchTagChipGroup);
        searchCategoryChipGroup = view.findViewById(R.id.searchCategoryChipGroup);
        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        chip = view.findViewById(R.id.searchAllCategoriesChip);
        chip.setOnClickListener(this);

        chip = view.findViewById(R.id.historicalChip);
        chip.setOnClickListener(this);
        chip.setTag("Historical");
        chip = view.findViewById(R.id.museumChip);
        chip.setOnClickListener(this);
        chip.setTag("Museum");
        chip = view.findViewById(R.id.naturalChip);
        chip.setOnClickListener(this);
        chip.setTag("Natural");
        chip = view.findViewById(R.id.restaurantsChip);
        chip.setOnClickListener(this);
        chip.setTag("Restaurants");
        chip = view.findViewById(R.id.localChip);
        chip.setOnClickListener(this);
        chip.setTag("Local");
        chip = view.findViewById(R.id.shoppingChip);
        chip.setOnClickListener(this);
        chip.setTag("Shopping");

        chip = view.findViewById(R.id.allTagsChip);
        chip.setTag("tag");
        chip.setOnClickListener(this);



        //setting governorateRecyclerView
        searchGovernoratesRecyclerView = view.findViewById(R.id.searchGovernoratesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        searchGovernoratesRecyclerView.setLayoutManager(layoutManager);
        adapter = new GovernoratesSearchRecyclerViewAdapter(governorates,view.getContext());
        searchGovernoratesRecyclerView.setAdapter(adapter);

        if(governorates.size() == 0)
            getGovernorate();

        if(governorates.size() == 0)
            getTags();

        return view;
    }


    void getGovernorate()
    {
        queryBuilder.setPageSize(100);

        Backendless.Data.of(Governorates.class).find(queryBuilder,new AsyncCallback<List<Governorates>>() {
            @Override
            public void handleResponse(List<Governorates> response) {
                governorates.addAll(response);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    void getTags()
    {
        queryBuilder.setPageSize(100);

        Backendless.Data.of(Tags.class).find(queryBuilder,new AsyncCallback<List<Tags>>() {
            @Override
            public void handleResponse(List<Tags> response) {
                addTagsChips(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }


    void addTagsChips(List<Tags> tagsList)
    {

        Chip chip ;

        for (Tags tag : tagsList) {
            chip = (Chip) getLayoutInflater().inflate(R.layout.tag,searchTagChipGroup,false);
            chip.setText(tag.getName());
            chip.setTag("tag");
            chip.setOnClickListener(this);
            searchTagChipGroup.addView(chip);

        }


    }



    @Override
    public void onClick(View v) {



        if (v.getId() == R.id.searchButton)
        {
            StringBuilder whereCase = new StringBuilder();
            if(adapter.getGovernorates().size() != 0)
            {
                whereCase.append("(");
                for (String adapterGovernorate : adapter.getGovernorates()) {
                    whereCase.append("governorate = '").append(adapterGovernorate).append("'").append("or ");
                }
                whereCase.delete(whereCase.length()-3,whereCase.length());
                whereCase.append(")");

            }

            Chip c ;

            if(searchTagChipGroup.getCheckedChipIds().get(0) != R.id.allTagsChip)
            {
                if(whereCase.length() != 0)
                    whereCase.append(" and (");
                else
                    whereCase.append("(");


                for (Integer checkedChipId : searchTagChipGroup.getCheckedChipIds()) {
                    c = getActivity().findViewById(checkedChipId);
                    whereCase.append("placeTags.name_EN = '").append(c.getText().toString()).append("'").append(" or ");
                }

                whereCase.delete(whereCase.length()-3,whereCase.length());
                whereCase.append(")");
            }


            if(searchCategoryChipGroup.getCheckedChipIds().get(0) != R.id.searchAllCategoriesChip)
            {
                if(whereCase.length() != 0)
                    whereCase.append(" and (");
                else
                    whereCase.append("(");


                for (Integer checkedChipId : searchCategoryChipGroup.getCheckedChipIds()) {
                    c = getActivity().findViewById(checkedChipId);
                    whereCase.append("category = '").append(c.getTag().toString()).append("'").append(" or ");
                }

                whereCase.delete(whereCase.length()-3,whereCase.length());
                whereCase.append(")");
            }

            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
            editor.putString("whereCase",whereCase.toString());
            editor.apply();

            //go back to MainActivity
            Intent intent;
            intent = new Intent(getActivity(), ResultsActivity.class);
            startActivity(intent);
            intent = null;


            Log.i("TAG", "onClick: "+whereCase.toString());
        }
        else
        {
            Chip c = (Chip) v;
            if(c.getTag() == null)
            {
                Chip all = getActivity().findViewById(R.id.searchAllCategoriesChip) ;

                if(c.getId() == R.id.searchAllCategoriesChip)
                {
                    if(all.isChecked())
                    {
                        searchCategoryChipGroup.clearCheck();

                        all.setChecked(true);
                    }

                }
                else
                {
                    all.setChecked(searchCategoryChipGroup.getCheckedChipIds().size() == 0);
                }


            }
            else
            {
                Chip all = getActivity().findViewById(R.id.allTagsChip) ;

                if(c.getId() == R.id.allTagsChip)
                {
                    if(all.isChecked())
                    {
                        searchTagChipGroup.clearCheck();

                        all.setChecked(true);
                    }

                }
                else
                {
                    all.setChecked(searchTagChipGroup.getCheckedChipIds().size() == 0);
                }
            }
        }

    }
}