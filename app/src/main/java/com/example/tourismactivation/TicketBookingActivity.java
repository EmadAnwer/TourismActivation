package com.example.tourismactivation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.tourismactivation.molde.Places;
import com.example.tourismactivation.molde.Tickets;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tourismactivation.constants.EN;
import static com.google.android.material.datepicker.MaterialDatePicker.todayInUtcMilliseconds;

public class TicketBookingActivity extends AppCompatActivity implements View.OnFocusChangeListener, TextWatcher, MaterialPickerOnPositiveButtonClickListener<Long> {
    TextInputLayout dateTextInputLayout, countTextInputLayout;
    MaterialDatePicker<Long> materialDatePicker;
    SharedPreferences pref;
    String nameAR,nameEN, typeAR,typeEN, id;
    Button bookingButton;
    TextView totalTicketsTextView, bookPlaceTextView, bookPlaceTypeTextView, bookPlacePriceTextView,bookingProgressTextView;
    byte count = 1;
    Short price = 0, total = 0;
    Date reservationDate;
    boolean userRelationsSets,placeRelationsSets;
    List<Tickets> ticketsList;
    ProgressWheel setupBookingProgressWheel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking);
        bookingButton = findViewById(R.id.bookingButton);
        bookPlaceTextView = findViewById(R.id.bookPlaceTextView);
        dateTextInputLayout = findViewById(R.id.dateTextInputLayout);
        totalTicketsTextView = findViewById(R.id.totalTicketsTextView);
        bookPlaceTypeTextView = findViewById(R.id.bookPlaceTypeTextView);
        bookPlacePriceTextView = findViewById(R.id.bookPlacePriceTextView);
        bookingProgressTextView = findViewById(R.id.bookingProgressTextView);
        setupBookingProgressWheel = findViewById(R.id.setupBookingProgressWheel);

        setupBookingProgressWheel.stopSpinning();

        dateTextInputLayout.getEditText().setInputType(InputType.TYPE_NULL);
        dateTextInputLayout.getEditText().setOnFocusChangeListener(this);

        countTextInputLayout = findViewById(R.id.countTextInputLayout);
        countTextInputLayout.getEditText().addTextChangedListener(this);
        countTextInputLayout.getEditText().setText("1");

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now());

        materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(todayInUtcMilliseconds())
                .setTitleText("SELECT A DATE")
                .setCalendarConstraints(constraintsBuilder.build())
                .build();
        materialDatePicker.addOnPositiveButtonClickListener(this);


        pref = getSharedPreferences("placesPref", Context.MODE_PRIVATE);
        id = pref.getString("placeID", "error");
        nameAR = pref.getString("placeNameAR", "error");
        nameEN = pref.getString("placeNameEN", "error");

        Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
        pref = getSharedPreferences("pricePref", Context.MODE_PRIVATE);
        price = (short) pref.getInt("priceCost", 0);
        typeAR = pref.getString("priceTypeAR", "error");
        typeEN = pref.getString("priceTypeEN", "error");


        if(constants.LANGUAGE == EN)
        {
            bookPlaceTextView.setText(nameEN);
            bookPlaceTypeTextView.setText(typeEN);
        }else
        {
            bookPlaceTextView.setText(nameAR);
            bookPlaceTypeTextView.setText(typeAR);
        }
        bookPlacePriceTextView.setText(price.toString());
        setTotal();

    }


    public void pickDate(View view) {
        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (v.getId() == dateTextInputLayout.getEditText().getId() && hasFocus) {
            materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        }
    }

    Boolean isValid() {
        bookingButton.setEnabled(false);
        bookingButton.setTextColor(Color.WHITE);

        if (dateTextInputLayout.getEditText().getText().toString().isEmpty())
            return false;
        else if (countTextInputLayout.getEditText().getText().toString().isEmpty())
            return false;
        else if (count > 10)
            return false;

        bookingButton.setEnabled(true);
        bookingButton.setTextColor(Color.BLACK);
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (countTextInputLayout.getEditText().getText().toString().isEmpty())
            count = 0;
        else
            count = Byte.parseByte(countTextInputLayout.getEditText().getText().toString());
        isValid();
        setTotal();

        Toast.makeText(this, "Count :" + count, Toast.LENGTH_SHORT).show();

    }

    void setTotal() {
        total = (short) (price * count);
        totalTicketsTextView.setText("");
        totalTicketsTextView.setText(total.toString());
        totalTicketsTextView.append(getString(R.string.priceType));
    }


    @Override
    public void onPositiveButtonClick(Long selection) {
        if (reservationDate != null)
            reservationDate = null;

        reservationDate = new Date(selection);
        dateTextInputLayout.getEditText().setText(materialDatePicker.getHeaderText());
        isValid();
    }

//public Tickets( price,  placeName_AR,  placeName_EN,  type_EN,  type_AR,  reservationDate) {
    List<Tickets> getTicketsList(short price, String placeName_AR,String placeName_EN ,String type_EN,String type_AR, Date reservationDate) {
        List<Tickets> ticketsList = new ArrayList<>();
        for (byte i = 0; i < count; i++) {

            ticketsList.add(new Tickets((int) price,placeName_AR,  placeName_EN,  type_EN,  type_AR , reservationDate));
        }

        return ticketsList;
    }


    void setTicketsIntoTable(List<Tickets> ticketsList) {

        Toast.makeText(this, "" + ticketsList.size(), Toast.LENGTH_SHORT).show();
        Backendless.Data.of(Tickets.class).create(ticketsList, new AsyncCallback<List<String>>() {

            @Override
            public void handleResponse(List<String> ticketsIDs) {

                setTicketsRelations(ticketsIDs);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    public void booking(View view) {
        // Check if no view has focus:
        View v = getCurrentFocus();
        if (v != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        setupBookingProgressWheel.spin();
        bookingProgressTextView.setText("booking is in process.");
        ticketsList = getTicketsList(price, nameAR,nameEN,typeEN,typeAR, reservationDate);
        setTicketsIntoTable(ticketsList);

    }

    void setTicketsRelations(List<String> ticketsIDs) {
        setTicketsRelationsUser(ticketsIDs);
        setTicketsRelationsPlaces(ticketsIDs);



    }

    void setTicketsRelationsUser(List<String> ticketsIDs)
    {
        Log.i("id", "handleResponse: " + Backendless.UserService.loggedInUser());

        Backendless.Data.of("Users").addRelation(Backendless.UserService.loggedInUser(), "placeTickets:Tickets:n", ticketsIDs, new AsyncCallback<Integer>() {
            @Override
            public void handleResponse(Integer response) {
                userRelationsSets= true;
                Log.i("id", "handleResponse: " + response.toString());

                endThisActivity(placeRelationsSets,userRelationsSets);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("id", "handleResponse: " + fault.toString());

            }
        });
    }

    void setTicketsRelationsPlaces(List<String> ticketsIDs)
    {
        Backendless.Data.of(Places.class).addRelation(id, "placeTickets:Tickets:n", ticketsIDs, new AsyncCallback<Integer>() {
            @Override
            public void handleResponse(Integer response) {
                placeRelationsSets = true;
                Log.i("id", "handleResponse: " + response.toString());

                endThisActivity(placeRelationsSets,userRelationsSets);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.i("id", "handleResponse: " + fault.toString());

            }
        });

    }


    void endThisActivity(boolean placeRelationsSets, boolean userRelationsSets)
    {
        if(placeRelationsSets && userRelationsSets)
        {
            Toast.makeText(this, "Booking done successfully", Toast.LENGTH_SHORT).show();
            onBackPressed();
            //TODO clean
        }
    }







}