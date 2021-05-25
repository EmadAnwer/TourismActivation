package com.example.tourismactivation.recyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.bumptech.glide.Glide;
import com.example.tourismactivation.R;

import com.example.tourismactivation.molde.Review;
import com.example.tourismactivation.molde.Reviews;
import com.google.android.material.imageview.ShapeableImageView;
import org.ocpsoft.prettytime.PrettyTime;
import java.util.Date;
import java.util.List;
import xyz.hanks.library.bang.SmallBangView;


public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    PrettyTime p = new PrettyTime();
    private final List<Reviews> reviewsList;
    SharedPreferences pref;
    Context context;
    public ReviewsRecyclerViewAdapter(List<Reviews> reviewsList, Context context) {
        this.reviewsList = reviewsList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_layout,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //header
        holder.reviewUserNameTextView.setText(reviewsList.get(position).getName());
        holder.reviewTimeTextView.setText(p.format(new Date(reviewsList.get(position).getCreated())));
        Glide.with(context)
                .asBitmap()
                .load(reviewsList.get(position).getUserProfilePicture())
                .into(holder.reviewUserProfilePictureImageView);

        if(!reviewsList.get(position).isPostedByCurrentUser())
            holder.deleteReviewTextView.setVisibility(View.GONE);

        //set content
        holder.reviewContentTextView.setText(reviewsList.get(position).getContent());
        holder.reviewRatingBar.setRating(reviewsList.get(position).getRate());

        holder.reviewsLikesCountTextView.setText(""+reviewsList.get(position).getTotalUsefulsCount());
        holder.reviewLoveImageViewAnimation.setSelected(reviewsList.get(position).isUsefuled());
        holder.reviewLoveImageViewAnimation.setOnClickListener(this);
        holder.deleteReviewTextView.setOnClickListener(this);
        holder.deleteReviewTextView.setTag(position);

        holder.reviewLoveImageViewAnimation.setTag(holder.reviewsLikesCountTextView);
        holder.reviewsLikesCountTextView.setTag(reviewsList.get(position));

    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.reviewLoveImageViewAnimation) {
            SmallBangView reviewLove = (SmallBangView) v;

            TextView countTextView = (TextView) reviewLove.getTag();
            Reviews r = (Reviews) countTextView.getTag();

            if (v.isSelected()) {
                reviewLove.setSelected(false);
                countTextView.setText("" + (r.UsefulsDecreased()));
                Review.getInstance().unlikeReviewAsync(Backendless.UserService.loggedInUser(), r.getPostId(), new AsyncCallback<Object>() {
                    @Override
                    public void handleResponse(Object response) {

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.i("TAG", "handleFault: " + fault.toString());
                        countTextView.setText("" + (r.UsefulsIncreased()));
                    }
                });

            } else {
                // if not selected only
                // then show animation.

                reviewLove.setSelected(true);
                countTextView.setText("" + (r.UsefulsIncreased()));
                reviewLove.likeAnimation();
                Review.getInstance().likeReviewAsync(Backendless.UserService.loggedInUser(), r.getPostId(), new AsyncCallback<Object>() {
                    @Override
                    public void handleResponse(Object response) {

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.i("TAG", "handleFault: " + fault.toString());
                        countTextView.setText("" + (r.UsefulsDecreased()));
                    }
                });
            }
        }
        else if(v.getId() == R.id.deleteReviewTextView)
        {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.DeletePost)
                    .setMessage(R.string.DeletePostDialog)
                    .setPositiveButton(R.string.yesDelete, (dialog, which) -> {
                        int position = (int) v.getTag();
                        String whereCase = "objectId ='"+reviewsList.get(position).getPostId()+"'";
                        Backendless.Data.of("Experiences").remove(whereCase, new AsyncCallback<Integer>() {
                            @Override
                            public void handleResponse(Integer response) {

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });

                        reviewsList.remove(position);
                        notifyDataSetChanged();
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
    }



    public static class ViewHolder  extends RecyclerView.ViewHolder{
        TextView reviewUserNameTextView,reviewTimeTextView,reviewContentTextView,reviewsLikesCountTextView,deleteReviewTextView;
        ShapeableImageView reviewUserProfilePictureImageView;
        SmallBangView reviewLoveImageViewAnimation;
        RatingBar reviewRatingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewRatingBar =itemView.findViewById(R.id.reviewRatingBar);
            reviewTimeTextView =itemView.findViewById(R.id.reviewTimeTextView);
            deleteReviewTextView =itemView.findViewById(R.id.deleteReviewTextView);
            reviewContentTextView =itemView.findViewById(R.id.reviewContentTextView);
            reviewUserNameTextView =itemView.findViewById(R.id.reviewUserNameTextView);
            reviewsLikesCountTextView =itemView.findViewById(R.id.reviewsLikesCountTextView);
            reviewLoveImageViewAnimation =itemView.findViewById(R.id.reviewLoveImageViewAnimation);
            reviewUserProfilePictureImageView =itemView.findViewById(R.id.reviewUserProfilePictureImageView);




        }
    }
}