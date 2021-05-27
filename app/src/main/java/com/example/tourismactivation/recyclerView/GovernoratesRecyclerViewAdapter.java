package com.example.tourismactivation.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.tourismactivation.molde.Governorates;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class GovernoratesRecyclerViewAdapter extends RecyclerView.Adapter<GovernoratesRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "GovernoratesRecyclerVie";
    SharedPreferences pref;
    //var
    private final List<Governorates> governoratesArrayList;
     Context context;

    public GovernoratesRecyclerViewAdapter(List<Governorates> governorateArrayList, Context context) {
        this.governoratesArrayList = governorateArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_governorate,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onCreateViewHolder: called.");
        Glide.with(context)
                .asBitmap()
                .load(governoratesArrayList.get(position).getCoverImage())
                .into(holder.governorateImageView);

        holder.governorateTextView.setText(governoratesArrayList.get(position).getName());
        holder.governorateImageView.setOnClickListener(this);
        holder.governorateImageView.setTag(governoratesArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return governoratesArrayList.size();
    }

    @Override
    public void onClick(View v) {
        Governorates g = (Governorates) v.getTag();

        // pass governorate name and governorate cover within SharedPreferences
        pref = context.getSharedPreferences("governoratePref", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
        editor.putString("governorateObjectId", g.getObjectId());
        editor.putString("coverImage", g.getCoverImage());
        editor.putString("governorate", g.getName());
        editor.apply();


        // intent to PlacesActivity
        Intent intent = new Intent(context, PlacesActivity.class);
        context.startActivity(intent);
        intent = null;


    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{
        ImageView governorateImageView;
        TextView governorateTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            governorateImageView =itemView.findViewById(R.id.governorateImageView);
            governorateTextView =itemView.findViewById(R.id.governorateTextView);

        }
    }
}
