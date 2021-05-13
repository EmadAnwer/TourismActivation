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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tourismactivation.PlacesActivity;
import com.example.tourismactivation.R;
import com.example.tourismactivation.RecommendedActivity;
import com.example.tourismactivation.molde.Governorates;
import com.example.tourismactivation.molde.Recommended;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecommendedRecyclerViewAdapter extends RecyclerView.Adapter<RecommendedRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    private static final String TAG = "RecommendedRecyclerViewAdapter";
    SharedPreferences pref;
    //var
    private final List<Recommended> recommendedList;
    Context context;

    public RecommendedRecyclerViewAdapter(List<Recommended> recommendedList, Context context) {
        this.recommendedList = recommendedList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecommendedRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_layout,parent,false);


        return new RecommendedRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedRecyclerViewAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(recommendedList.get(position).getCover_Image())
                .into(holder.recommendedCoverImageView);

        holder.recommendedTextView.setText(recommendedList.get(position).getName());
        holder.recommendedCoverImageView.setOnClickListener(this);
        holder.recommendedCoverImageView.setTag(recommendedList.get(position));
    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    @Override
    public void onClick(View v) {
        Recommended g = (Recommended) v.getTag();
        Toast.makeText(context, g.getName(), Toast.LENGTH_SHORT).show();

        // pass Recommended name and Recommended cover within SharedPreferences
        pref = context.getSharedPreferences("recommendedPref", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
        editor.putString("recommendedObjectId", g.getObjectId());
        editor.putString("coverImage", g.getCover_Image());
        editor.putString("recommendedName", g.getName());
        editor.putString("recommendedDescription", g.getDescription());

        editor.apply();


        // intent to PlacesActivity
        Intent intent = new Intent(context, RecommendedActivity.class);
        context.startActivity(intent);
        intent = null;


    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{
        ImageView recommendedCoverImageView;
        TextView recommendedTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recommendedCoverImageView =itemView.findViewById(R.id.recommendedCoverImageView);
            recommendedTextView =itemView.findViewById(R.id.recommendedTextView);

        }
    }
}
