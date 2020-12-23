package com.poly.smartfindpro.data.model.product.deleteProduct.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteProductRequest {
    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("userId")
    @Expose
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
