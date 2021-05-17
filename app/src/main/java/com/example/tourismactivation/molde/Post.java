package com.example.tourismactivation.molde;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("totalLikesCount")
    @Expose
    private int totalLikesCount;
    @SerializedName("created")
    @Expose
    private long created;
    @SerializedName("userProfilePicture")
    @Expose
    private String userProfilePicture;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("postId")
    @Expose
    private String postId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("liked")
    @Expose
    private boolean liked;
    @SerializedName("postedByCurrentUser")
    @Expose
    private boolean postedByCurrentUser;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalLikesCount() {
        return totalLikesCount;
    }

    public void setTotalLikesCount(int totalLikesCount) {
        this.totalLikesCount = totalLikesCount;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public int LikesIncreased() {
        totalLikesCount +=1;
        return totalLikesCount;
    }

    public int LikesDecreased() {
        totalLikesCount -=1;
        return totalLikesCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isPostedByCurrentUser() {
        return postedByCurrentUser;
    }

    public void setPostedByCurrentUser(boolean postedByCurrentUser) {
        this.postedByCurrentUser = postedByCurrentUser;
    }
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Post.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("image");
        sb.append('=');
        sb.append(((this.image == null)?"<null>":this.image));
        sb.append(',');
        sb.append("totalLikesCount");
        sb.append('=');
        sb.append(this.totalLikesCount);
        sb.append(',');
        sb.append("created");
        sb.append('=');
        sb.append(this.created);
        sb.append(',');
        sb.append("userProfilePicture");
        sb.append('=');
        sb.append(((this.userProfilePicture == null)?"<null>":this.userProfilePicture));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("content");
        sb.append('=');
        sb.append(((this.content == null)?"<null>":this.content));
        sb.append(',');
        sb.append("liked");
        sb.append('=');
        sb.append(this.liked);
        sb.append(',');
        sb.append("postedByCurrentUser");
        sb.append('=');
        sb.append(this.postedByCurrentUser);
        sb.append(',');
        sb.append("postId");
        sb.append('=');
        sb.append(this.postId);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}