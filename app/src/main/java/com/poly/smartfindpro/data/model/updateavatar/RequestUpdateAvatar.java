package com.poly.smartfindpro.data.model.updateavatar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestUpdateAvatar {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public RequestUpdateAvatar() {
    }

    public RequestUpdateAvatar(String userId, String avatar) {
        this.userId = userId;
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
