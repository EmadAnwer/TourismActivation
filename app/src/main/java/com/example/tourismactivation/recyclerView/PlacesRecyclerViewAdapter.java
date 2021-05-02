package com.example.tourismactivation.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tourismactivation.PlaceActivity;
import com.example.tourismactivation.PlacesActivity;
import com.example.tourismactivation.R;
import com.example.tourismactivation.molde.Places;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class PlacesRecyclerViewAdapter extends RecyclerView.Adapter<PlacesRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "PlacesRecyclerVie";
    SharedPreferences pref;
    //var
    private List<Places> placesArrayList = new ArrayList<>();
    Context context;

    public PlacesRecyclerViewAdapter(List<Places>placesArrayList, Context context) {
        this.placesArrayList = placesArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_place,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onCreateViewHolder: called.");
        Glide.with(context)
                .asBitmap()
                .load(placesArrayList.get(position).getCover_image())
                .into(holder.placeCoverImageView);

        holder.placeRatingBar.setRating(placesArrayList.get(position).getRating_average());
        holder.placeTextView.setText(placesArrayList.get(position).getName());
        holder.placeCoverImageView.setOnClickListener(this);
        holder.placeCoverImageView.setTag(placesArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return placesArrayList.size();
    }

    @Override
    public void onClick(View v) {
        Places p = (Places) v.getTag();
        Toast.makeText(context, p.getName(), Toast.LENGTH_SHORT).show();

        // pass governorate name and governorate cover within SharedPreferences
        pref = context.getSharedPreferences("placesPref", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
        editor.putString("coverImage", p.getCover_image());
        editor.putString("governorate", p.getName());
        editor.apply();


        // intent to PlacesActivity
        Intent intent = new Intent(context, PlaceActivity.class);
        context.startActivity(intent);
        intent = null;


    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{
        ImageView placeCoverImageView;
        TextView placeTextView;
        RatingBar placeRatingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeCoverImageView =itemView.findViewById(R.id.placeCoverImageView);
            placeTextView =itemView.findViewById(R.id.placeTextView);
            placeRatingBar =itemView.findViewById(R.id.placeRatingBar);

        }
    }
}
