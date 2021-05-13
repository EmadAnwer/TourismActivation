package com.example.tourismactivation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RecommendedOverviewFragment extends Fragment {

    TextView RecommendedDescriptionTextView;
    SharedPreferences pref;
    String description;
    public RecommendedOverviewFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_recommended_overview, container, false);
        RecommendedDescriptionTextView = view.findViewById(R.id.RecommendedDescriptionTextView);
        pref = this.getActivity().getSharedPreferences("recommendedPref", Context.MODE_PRIVATE);
        description = pref.getString("recommendedDescription","error");
        RecommendedDescriptionTextView.setText(description);

        return view;
    }
}