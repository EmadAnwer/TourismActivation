package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.tourismactivation.molde.Places;
import com.example.tourismactivation.recyclerView.PlacesRecyclerViewAdapter;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences pref;
    RecyclerView resultsRecyclerView;
    TextView noResultsTextView;
    Toolbar resultsToolbar;
    ProgressWheel resultsProgressWheel;

    String whereCase = "";
    PlacesRecyclerViewAdapter adapter;
    List<Places> places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        pref = getSharedPreferences("search", Context.MODE_PRIVATE);
        whereCase = pref.getString("whereCase","");
        resultsRecyclerView = findViewById(R.id.resultsRecyclerView);
        resultsProgressWheel = findViewById(R.id.resultsProgressWheel);
        noResultsTextView = findViewById(R.id.noResultsTextView);
        resultsToolbar =findViewById(R.id.resultsToolbar);
        resultsToolbar.setNavigationOnClickListener(this);
        //setting RecyclerView
        resultsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        resultsRecyclerView.setLayoutManager(layoutManager);
        adapter = new PlacesRecyclerViewAdapter(places, this);
        resultsRecyclerView.setAdapter(adapter);

        getResults();
    }

    void  getResults()
    {
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereCase);
        queryBuilder.setPageSize(100);

        Backendless.Data.of(Places.class).find(queryBuilder, new AsyncCallback<List<Places>>() {
            @Override
            public void handleResponse(List<Places> response) {
                resultsProgressWheel.stopSpinning();
                resultsProgressWheel.setVisibility(View.GONE);
                if(response.size() == 0)
                    noResultsTextView.setVisibility(View.VISIBLE);
                places.addAll(response);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}