package com.poly.smartfindpro.data.model.profile.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.poly.smartfindpro.data.model.home.res.Address;
import com.poly.smartfindpro.data.model.updateaddress.AddressUpdate;
import com.poly.smartfindpro.data.model.updateaddress.RequestUpdateAddress;

public class UserRequest {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("full_name")
    @Expose
    private String fullname;

    @SerializedName("address")
    @Expose
    private AddressUpdate address;

    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birth")
    @Expose
    private String birth;

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public AddressUpdate getAddress() {
        return address;
    }

    public void setAddress(AddressUpdate address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
