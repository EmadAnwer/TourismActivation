package com.example.tourismactivation.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismactivation.R;
import com.example.tourismactivation.TicketBookingActivity;
import com.example.tourismactivation.molde.Prices;
import com.example.tourismactivation.molde.Tickets;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TicketsRecyclerViewAdapter extends RecyclerView.Adapter<TicketsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "TicketsRecyclerVie";
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private final List<Tickets> ticketsList;
    Context context;

    public TicketsRecyclerViewAdapter(List<Tickets> ticketsList, Context context) {
        this.ticketsList = ticketsList;
        this.context = context;
    }


    @NonNull
    @Override
    public TicketsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_layout,parent,false);


        return new TicketsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ticketPlaceNameTextView.setText(ticketsList.get(position).getPlaceName());
        holder.ticketTypeNameTextView.setText(ticketsList.get(position).getType());
        holder.ticketDateTextView.setText(formatter.format(ticketsList.get(position).getReservationDate()));
        holder.ticketPriceTextView.setText(ticketsList.get(position).getPrice().toString());
        holder.ticketPriceTextView.append("LE");

        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            BitMatrix matrix = writer.encode(ticketsList.get(position).getObjectId(),
                    BarcodeFormat.QR_CODE,
                    200,
                    200);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);

            holder.QrImageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return ticketsList.size();
    }




    public static class ViewHolder  extends RecyclerView.ViewHolder{
        TextView ticketPlaceNameTextView,ticketTypeNameTextView,ticketDateTextView,ticketPriceTextView;
        ImageView QrImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketPlaceNameTextView =itemView.findViewById(R.id.ticketPlaceNameTextView);
            ticketTypeNameTextView =itemView.findViewById(R.id.ticketTypeNameTextView);
            ticketDateTextView =itemView.findViewById(R.id.ticketDateTextView);
            ticketPriceTextView =itemView.findViewById(R.id.ticketPriceTextView);
            QrImageView =itemView.findViewById(R.id.QrImageView);


        }
    }
}

