package com.poly.smartfindpro.data.model.register.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckPhoneNumberRequest {
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
