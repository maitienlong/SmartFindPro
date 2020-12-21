package com.poly.smartfindpro.data.model.comment.getcomment.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentRequest {
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("product")
    @Expose
    private String product;

    public CommentRequest() {
    }

    public CommentRequest(String user, String product) {
        this.user = user;
        this.product = product;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
