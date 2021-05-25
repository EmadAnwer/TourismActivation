package com.example.tourismactivation.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tourismactivation.HomeExperiencesFragment;
import com.example.tourismactivation.PlacesActivity;
import com.example.tourismactivation.R;
import com.example.tourismactivation.ZoomImageActivity;
import com.example.tourismactivation.molde.Governorates;
import com.example.tourismactivation.molde.Images;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ImagesRecyclerViewAdapter extends RecyclerView.Adapter<ImagesRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    SharedPreferences pref;
    //var
    private final List<Images> ImagesArrayList;
    Context context;
    Fragment fragment;

    public ImagesRecyclerViewAdapter(List<Images> ImagesArrayList, Context context,Fragment fragment) {
        this.ImagesArrayList = ImagesArrayList;
        this.context = context;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position == 0)
        {
            holder.imageLayoutTextView.setText(R.string.addImage);
            holder.imageLayoutImageView.setOnClickListener(this);
            holder.imageLayoutImageView.setTag("");
            holder.imageLayoutImageView.setImageResource(R.drawable.ic_image);
        }

        else
        {
            Glide.with(context)
                    .asBitmap()
                    .load(ImagesArrayList.get(position).getSrc())
                    .into(holder.imageLayoutImageView);
            holder.imageLayoutImageView.setOnClickListener(this);
            holder.imageLayoutImageView.setTag(ImagesArrayList.get(position).getSrc());
            holder.imageLayoutTextView.setText(ImagesArrayList.get(position).getUser_name());
        }


    }

    @Override
    public int getItemCount() {
        return ImagesArrayList.size();
    }


    @Override
    public void onClick(View v) {



        String g = (String) v.getTag();
        if(g.isEmpty())
        {
            ImagePicker.Companion.with(fragment)
                    .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                    .compress(515)			        //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        }
        else
        {
            // pass governorate name and governorate cover within SharedPreferences
            pref = context.getSharedPreferences("GalleryPref", MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
            editor.putString("Image", g);
            editor.apply();
            // intent to PlacesActivity
            Intent intent = new Intent(context, ZoomImageActivity.class);
            context.startActivity(intent);
            intent = null;
        }




    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{
        ShapeableImageView imageLayoutImageView;
        TextView imageLayoutTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageLayoutImageView =itemView.findViewById(R.id.imageLayoutImageView);
            imageLayoutTextView =itemView.findViewById(R.id.imageLayoutTextView);

        }
    }
}