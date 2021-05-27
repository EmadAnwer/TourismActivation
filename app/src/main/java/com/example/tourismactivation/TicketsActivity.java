package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.tourismactivation.molde.Governorates;
import com.example.tourismactivation.molde.Places;
import com.example.tourismactivation.molde.Tickets;
import com.example.tourismactivation.molde.Users;
import com.example.tourismactivation.recyclerView.PlacesRecyclerViewAdapter;
import com.example.tourismactivation.recyclerView.TicketsRecyclerViewAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.paginate.Paginate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TicketsActivity extends AppCompatActivity implements View.OnClickListener, ChipGroup.OnCheckedChangeListener {
    List<Tickets> tickets = new ArrayList<>();

    List<Tickets> nextTickets , expiredTickets,userTickets;
    @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    String userID;
    ChipGroup ticketsFilterChipGroup;
    RecyclerView ticketsRecyclerView;
    TextView noTicketsTextView;
    Toolbar ticketsToolbar;
    int selectedId;
    Date today,oneDayBeforeToday;
    TicketsRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_YEAR,-1);
        oneDayBeforeToday= cal.getTime();


        //setting view's id's
        ticketsToolbar = findViewById(R.id.ticketsToolbar);
        noTicketsTextView = findViewById(R.id.noTicketsTextView);
        ticketsRecyclerView = findViewById(R.id.ticketsRecyclerView);
        ticketsFilterChipGroup = findViewById(R.id.ticketsFilterChipGroup);

        //setting toolbar -> back
        ticketsToolbar.setNavigationOnClickListener(this);


        //setting tickets Filter Group Checked Listener
        ticketsFilterChipGroup.setOnCheckedChangeListener(this);
        selectedId = ticketsFilterChipGroup.getCheckedChipId();


        ticketsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        ticketsRecyclerView.setLayoutManager(layoutManager);
        adapter = new TicketsRecyclerViewAdapter(tickets,this);
        ticketsRecyclerView.setAdapter(adapter);

        getNext();

    }

    DataQueryBuilder queryBuilder = DataQueryBuilder.create();


    void getNext()
    {
        tickets.clear();
        adapter.notifyDataSetChanged();

        if(nextTickets == null)
        {
            userID = Backendless.UserService.loggedInUser();
            queryBuilder.setSortBy("ReservationDate");
            queryBuilder.setWhereClause("ReservationDate  after  '"+formatter.format(oneDayBeforeToday)+("'") + " and ownerId= '"+userID+"'");
            Backendless.Data.of(Tickets.class).find(queryBuilder, new AsyncCallback<List<Tickets>>() {


                @Override
                public void handleResponse(List<Tickets> response) {
                    nextTickets = response;
                    tickets.addAll(nextTickets);
                    adapter.notifyDataSetChanged();
                    setNoTickets();
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });
        }else
        {
            tickets.addAll(nextTickets);
            adapter.notifyDataSetChanged();
            setNoTickets();
        }
    }




    void getExpired()
    {
        tickets.clear();
        adapter.notifyDataSetChanged();

        if(expiredTickets == null)
        {
            userID = Backendless.UserService.loggedInUser();
            queryBuilder.setSortBy("ReservationDate");
            queryBuilder.setWhereClause("ReservationDate  before  '"+formatter.format(today)+("'") + " and ownerId= '"+userID+"'");
            Backendless.Data.of(Tickets.class).find(queryBuilder, new AsyncCallback<List<Tickets>>() {


                @Override
                public void handleResponse(List<Tickets> response) {
                    expiredTickets = response;
                    tickets.addAll(expiredTickets);
                    adapter.notifyDataSetChanged();
                    setNoTickets();
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });
        }else
        {
            tickets.addAll(expiredTickets);
            adapter.notifyDataSetChanged();
            setNoTickets();
        }


    }


    void setNoTickets()
    {
        if(tickets.size() == 0)
            noTicketsTextView.setVisibility(View.VISIBLE);
        else
            noTicketsTextView.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        //TODO nullify data
        finish();
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

        if(checkedId == R.id.nextTicketsChip)
        {
            getNext();

        }
        else if(checkedId == R.id.expiredTicketsChip)
        {
            getExpired();
        }



    }
}