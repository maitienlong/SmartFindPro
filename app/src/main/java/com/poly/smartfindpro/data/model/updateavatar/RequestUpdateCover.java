package com.poly.smartfindpro.data.model.updateavatar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestUpdateCover {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("coverImage")
    @Expose
    private String coverImage;

    public RequestUpdateCover() {
    }

    public RequestUpdateCover(String userId, String coverImage) {
        this.userId = userId;
        this.coverImage = coverImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
