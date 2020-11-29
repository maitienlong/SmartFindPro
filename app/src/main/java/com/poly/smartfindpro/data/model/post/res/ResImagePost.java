package com.poly.smartfindpro.data.model.post.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResImagePost {
    @SerializedName("addressImage")
    @Expose
    private List<String> addressImage;

    public List<String> getAddressImage() {
        return addressImage;
    }

    public void setAddressImage(List<String> addressImage) {
        this.addressImage = addressImage;
    }
}
