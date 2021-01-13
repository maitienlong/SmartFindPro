package com.poly.smartfindpro.data.model.updateaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.poly.smartfindpro.data.model.base.Location;
import com.poly.smartfindpro.data.model.home.res.Address;

public class RequestUpdateAddress {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("address")
    @Expose
    private AddressUpdate address;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AddressUpdate getAddress() {
        return address;
    }

    public void setAddress(AddressUpdate address) {
        this.address = address;
    }
}
