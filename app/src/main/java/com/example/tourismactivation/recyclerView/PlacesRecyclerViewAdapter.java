package com.example.tourismactivation.recyclerView;

import android.content.Context;
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
import com.example.tourismactivation.R;
import com.example.tourismactivation.molde.Places;

import java.util.ArrayList;
import java.util.List;


public class PlacesRecyclerViewAdapter extends RecyclerView.Adapter<PlacesRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "PlacesRecyclerVie";

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
