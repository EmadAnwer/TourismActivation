package com.example.tourismactivation.recyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.bumptech.glide.Glide;
import com.example.tourismactivation.R;
import com.example.tourismactivation.TicketBookingActivity;
import com.example.tourismactivation.constants;
import com.example.tourismactivation.molde.Post;
import com.example.tourismactivation.molde.Prices;
import com.example.tourismactivation.services.Posts;
import com.google.android.material.imageview.ShapeableImageView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;

import xyz.hanks.library.bang.SmallBangView;

import static android.content.Context.MODE_PRIVATE;


public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<PostsRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "PricesRecyclerVie";
    PrettyTime p = new PrettyTime();
    private final List<Post> postList;
    SharedPreferences pref;
    Context context;
    public PostsRecyclerViewAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //header
        holder.postUserNameTextView.setText(postList.get(position).getName());
        holder.postTimeTextView.setText(p.format(new Date(postList.get(position).getCreated())));
        Glide.with(context)
                .asBitmap()
                .load(postList.get(position).getUserProfilePicture())
                .into(holder.postUserProfilePictureImageView);

        if(!postList.get(position).isPostedByCurrentUser())
            holder.deletePostTextView.setVisibility(View.GONE);

        //set content
        holder.postContentTextView.setText(postList.get(position).getContent());

        if(postList.get(position).getImage() == null)
            holder.postImageView.setVisibility(View.GONE);
        else
            Glide.with(context).asBitmap().load(postList.get(position).getImage()).into(holder.postImageView);

        holder.likesCountTextView.setText(""+postList.get(position).getTotalLikesCount());
        holder.postLoveImageViewAnimation.setSelected(postList.get(position).isLiked());
        holder.postLoveImageViewAnimation.setOnClickListener(this);
        holder.deletePostTextView.setOnClickListener(this);
        holder.deletePostTextView.setTag(position);

        holder.postLoveImageViewAnimation.setTag(holder.likesCountTextView);
        holder.likesCountTextView.setTag(postList.get(position));

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.postLoveImageViewAnimation) {
            SmallBangView postLove = (SmallBangView) v;

            TextView countTextView = (TextView) postLove.getTag();
            Post p = (Post) countTextView.getTag();

            if (v.isSelected()) {
                postLove.setSelected(false);
                countTextView.setText("" + (p.LikesDecreased()));
                Posts.getInstance().unlikePostAsync(Backendless.UserService.loggedInUser(), p.getPostId(), new AsyncCallback<Object>() {
                    @Override
                    public void handleResponse(Object response) {

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.i(TAG, "handleFault: " + fault.toString());
                        countTextView.setText("" + (p.LikesIncreased()));
                    }
                });

            } else {
                // if not selected only
                // then show animation.

                postLove.setSelected(true);
                countTextView.setText("" + (p.LikesIncreased()));
                postLove.likeAnimation();
                Posts.getInstance().likePostAsync(Backendless.UserService.loggedInUser(), p.getPostId(), new AsyncCallback<Object>() {
                    @Override
                    public void handleResponse(Object response) {

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.i(TAG, "handleFault: " + fault.toString());
                        countTextView.setText("" + (p.LikesDecreased()));
                    }
                });
            }
        }
        else if(v.getId() == R.id.deletePostTextView)
        {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.DeletePost)
                    .setMessage(R.string.DeletePostDialog)
                    .setPositiveButton(R.string.yesDelete, (dialog, which) -> {
                        int position = (int) v.getTag();
                        String whereCase = "objectId ='"+postList.get(position).getPostId()+"'";
                        Backendless.Data.of("Experiences").remove(whereCase, new AsyncCallback<Integer>() {
                            @Override
                            public void handleResponse(Integer response) {

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });

                        postList.remove(position);
                        notifyDataSetChanged();
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
    }



    public static class ViewHolder  extends RecyclerView.ViewHolder{
        TextView postUserNameTextView,postTimeTextView,postContentTextView,likesCountTextView,deletePostTextView;
        ShapeableImageView postUserProfilePictureImageView,postImageView;
        SmallBangView postLoveImageViewAnimation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImageView =itemView.findViewById(R.id.postImageView);
            postTimeTextView =itemView.findViewById(R.id.postTimeTextView);
            likesCountTextView =itemView.findViewById(R.id.likesCountTextView);
            deletePostTextView =itemView.findViewById(R.id.deletePostTextView);
            postContentTextView =itemView.findViewById(R.id.postContentTextView);
            postUserNameTextView =itemView.findViewById(R.id.postUserNameTextView);
            postLoveImageViewAnimation =itemView.findViewById(R.id.postLoveImageViewAnimation);
            postUserProfilePictureImageView =itemView.findViewById(R.id.postUserProfilePictureImageView);




        }
    }
}