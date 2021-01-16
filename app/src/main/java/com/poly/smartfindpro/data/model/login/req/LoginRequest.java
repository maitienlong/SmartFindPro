package com.poly.smartfindpro.data.model.login.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("account")
    @Expose
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("deviceId")
    @Expose
    private String deviceId;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
