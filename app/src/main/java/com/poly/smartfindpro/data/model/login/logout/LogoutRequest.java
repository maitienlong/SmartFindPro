package com.poly.smartfindpro.data.model.login.logout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogoutRequest {
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("deviceId")
    @Expose
    private String deviceId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
