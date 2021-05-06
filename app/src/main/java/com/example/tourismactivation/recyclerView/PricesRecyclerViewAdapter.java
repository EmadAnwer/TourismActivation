package com.example.tourismactivation.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tourismactivation.PlaceActivity;
import com.example.tourismactivation.R;
import com.example.tourismactivation.TicketBookingActivity;
import com.example.tourismactivation.molde.Places;

import java.util.List;
import static android.content.Context.MODE_PRIVATE;
import com.example.tourismactivation.molde.Prices;


public class PricesRecyclerViewAdapter extends RecyclerView.Adapter<PricesRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "PricesRecyclerVie";
    private final List<Prices> pricesArrayList;
    SharedPreferences pref;
    Context context;

    public PricesRecyclerViewAdapter(List<Prices> pricesArrayList, Context context) {
        this.pricesArrayList = pricesArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.price_layout,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ticketTypeTextView.setText(pricesArrayList.get(position).getTicketType());
        holder.ticketCostTextView.setText(pricesArrayList.get(position).getTicketCost()+"LE");
        holder.bookButton.setOnClickListener(this);
        holder.bookButton.setTag(pricesArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return pricesArrayList.size();
    }

    @Override
    public void onClick(View v) {
        Prices p = (Prices) v.getTag();
        // pass governorate name and governorate cover within SharedPreferences
        pref = context.getSharedPreferences("pricePref", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
        editor.putInt("priceCost", p.getTicketCost());
        editor.putString("priceType", p.getTicketType());
        editor.apply();


        // intent to PlacesActivity
        Intent intent = new Intent(context, TicketBookingActivity.class);
        context.startActivity(intent);
        intent = null;

    }


    public static class ViewHolder  extends RecyclerView.ViewHolder{
        TextView ticketTypeTextView,ticketCostTextView;
        Button bookButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketTypeTextView =itemView.findViewById(R.id.ticketTypeTextView);
            ticketCostTextView =itemView.findViewById(R.id.ticketCostTextView);
            bookButton =itemView.findViewById(R.id.bookButton);

        }
    }
}