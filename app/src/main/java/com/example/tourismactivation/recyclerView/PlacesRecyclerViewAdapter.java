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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class PlacesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "PlacesRecyclerVie";
    private static final byte VIEW_TYPE_LOADING = 0;
    private static final byte VIEW_TYPE_PLACE = 1;


    SharedPreferences pref;
    //var
    private final List<Places> placesArrayList;
    Context context;

    public PlacesRecyclerViewAdapter(List<Places>placesArrayList, Context context) {
        this.placesArrayList = placesArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if(viewType == VIEW_TYPE_PLACE)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_place,parent,false);


            return new ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_layout,parent,false);

            return new LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder)
            setPlaceHolder((ViewHolder) holder, position);


    }


    private void setPlaceHolder(ViewHolder holder, int position) {
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
    public int getItemViewType(int position) {
        return placesArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_PLACE;
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
        editor.putString("placeName", p.getName());
        editor.putString("placeNameAR", p.getName_AR());
        editor.putString("placeNameEN", p.getName_EN());
        editor.putString("placeID", p.getObjectId());
        Log.i(TAG, "onClick: "+p.getObjectId());
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


    public static class LoadingViewHolder  extends RecyclerView.ViewHolder{

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
