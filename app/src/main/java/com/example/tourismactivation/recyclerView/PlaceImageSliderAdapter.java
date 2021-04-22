package com.example.tourismactivation.recyclerView;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tourismactivation.R;
import com.example.tourismactivation.molde.Images;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlaceImageSliderAdapter extends
        SliderViewAdapter<PlaceImageSliderAdapter.SliderAdapterVH> {

    private final Context context;
    private List<Images> imagesSrcItems = new ArrayList<>();

    public PlaceImageSliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<Images> sliderItems) {
        this.imagesSrcItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.imagesSrcItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Images image) {
        this.imagesSrcItems.add(image);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.plcae_image_layout,parent, false);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        Images image = imagesSrcItems.get(position);

        Glide.with(viewHolder.itemView)
                .load(image.getSrc())
                .fitCenter()
                .into(viewHolder.placeImageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return imagesSrcItems.size();
    }

    static class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView placeImageView;


        public SliderAdapterVH(View itemView) {
            super(itemView);
            placeImageView = itemView.findViewById(R.id.placeImageView);
            this.itemView = itemView;
        }
    }

}