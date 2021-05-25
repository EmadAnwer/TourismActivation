package com.example.tourismactivation.molde;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reviews {

    @SerializedName("totalUsefulsCount")
    @Expose
    private int totalUsefulsCount;
    @SerializedName("rate")
    @Expose
    private float rate;
    @SerializedName("created")
    @Expose
    private long created;
    @SerializedName("usefuled")
    @Expose
    private boolean usefuled;
    @SerializedName("userProfilePicture")
    @Expose
    private String userProfilePicture;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("postedByCurrentUser")
    @Expose
    private boolean postedByCurrentUser;
    @SerializedName("postId")
    @Expose
    private String postId;
    @SerializedName("content")
    @Expose
    private String content;

    public int getTotalUsefulsCount() {
        return totalUsefulsCount;
    }

    public void setTotalUsefulsCount(int totalUsefulsCount) {
        this.totalUsefulsCount = totalUsefulsCount;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public boolean isUsefuled() {
        return usefuled;
    }

    public void setUsefuled(boolean usefuled) {
        this.usefuled = usefuled;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public int UsefulsIncreased() {
        totalUsefulsCount +=1;
        return totalUsefulsCount;
    }

    public int UsefulsDecreased() {
        totalUsefulsCount -=1;
        return totalUsefulsCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Reviews.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("totalUsefulsCount");
        sb.append('=');
        sb.append(this.totalUsefulsCount);
        sb.append(',');
        sb.append("rate");
        sb.append('=');
        sb.append(this.rate);
        sb.append(',');
        sb.append("created");
        sb.append('=');
        sb.append(this.created);
        sb.append(',');
        sb.append("usefuled");
        sb.append('=');
        sb.append(this.usefuled);
        sb.append(',');
        sb.append("userProfilePicture");
        sb.append('=');
        sb.append(((this.userProfilePicture == null)?"<null>":this.userProfilePicture));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("postedByCurrentUser");
        sb.append('=');
        sb.append(this.postedByCurrentUser);
        sb.append(',');
        sb.append("postId");
        sb.append('=');
        sb.append(((this.postId == null)?"<null>":this.postId));
        sb.append(',');
        sb.append("content");
        sb.append('=');
        sb.append(((this.content == null)?"<null>":this.content));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}