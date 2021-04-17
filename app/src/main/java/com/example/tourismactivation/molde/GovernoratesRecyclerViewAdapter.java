package com.example.tourismactivation.molde;

import android.content.Context;
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
import com.example.tourismactivation.R;

import java.util.ArrayList;

public class GovernoratesRecyclerViewAdapter extends RecyclerView.Adapter<GovernoratesRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "GovernoratesRecyclerVie";

    //var
    private ArrayList<Governorate> governorateArrayList = new ArrayList<>();
     Context context;

    public GovernoratesRecyclerViewAdapter(ArrayList<Governorate> governorateArrayList, Context context) {
        this.governorateArrayList = governorateArrayList;
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
                .load(governorateArrayList.get(position).getImg())
                .into(holder.governorateImageView);

        holder.governorateTextView.setText(governorateArrayList.get(position).getName());
        holder.governorateImageView.setOnClickListener(this);
        holder.governorateImageView.setTag(governorateArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return governorateArrayList.size();
    }

    @Override
    public void onClick(View v) {
        Governorate g = (Governorate) v.getTag();
        Toast.makeText(context, g.getName(), Toast.LENGTH_SHORT).show();

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
