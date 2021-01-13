package com.poly.smartfindpro.data.model.forgotpasswrd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.poly.smartfindpro.data.model.base.ResponseHeader;
import com.poly.smartfindpro.data.model.uploadphoto.ResponseBody;

public class ForgotPasswordRequest {
    @SerializedName("phone_number")
    @Expose
    private String phone_number;
    @SerializedName("password")
    @Expose
    private String password;

    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(String phone_number, String password) {
        this.phone_number = phone_number;
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
