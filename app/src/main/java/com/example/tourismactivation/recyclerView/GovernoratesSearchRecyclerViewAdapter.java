package com.example.tourismactivation.recyclerView;

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
import com.example.tourismactivation.molde.Governorates;

import java.util.ArrayList;
import java.util.List;


public class GovernoratesSearchRecyclerViewAdapter extends RecyclerView.Adapter<GovernoratesSearchRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "GovernoratesRecyclerVie";
    //var
    private final List<Governorates> governoratesArrayList;
    List<String> governorates = new ArrayList<>();
    Context context;

    public GovernoratesSearchRecyclerViewAdapter(List<Governorates> governorateArrayList, Context context) {
        this.governoratesArrayList = governorateArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_governorate,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onCreateViewHolder: called.");
        Glide.with(context)
                .asBitmap()
                .load(governoratesArrayList.get(position).getCoverImage())
                .into(holder.governorateSearchImageView);

        if(governorates.contains(governoratesArrayList.get(position).getName_EN()))
            holder.governorateSearchImageView.setAlpha(0.5f);
        else
            holder.governorateSearchImageView.setAlpha(1f);

        holder.governorateSearchTextView.setText(governoratesArrayList.get(position).getName());
        holder.governorateSearchImageView.setOnClickListener(this);
        holder.governorateSearchImageView.setTag(governoratesArrayList.get(position));
    }

    public List<String> getGovernorates()
    {

        return governorates;
    }

    @Override
    public int getItemCount() {
        return governoratesArrayList.size();
    }

    @Override
    public void onClick(View v) {
        Governorates g = (Governorates) v.getTag();

        if(v.getAlpha() != 0.5f)
        {
            v.setAlpha(0.5f);
            governorates.add(g.getName_EN());
        }
        else
        {
            v.setAlpha(1);
            governorates.remove(g.getName_EN());

        }





    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{
        ImageView governorateSearchImageView;
        TextView governorateSearchTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            governorateSearchImageView =itemView.findViewById(R.id.governorateSearchImageView);
            governorateSearchTextView =itemView.findViewById(R.id.governorateSearchTextView);

        }
    }
}
