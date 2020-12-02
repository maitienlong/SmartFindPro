package com.poly.smartfindpro.data.model.product.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductRequest {
    @SerializedName("userId")
    @Expose
    private String userId;

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
    }
}
