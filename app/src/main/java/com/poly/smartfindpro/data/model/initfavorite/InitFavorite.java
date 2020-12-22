package com.poly.smartfindpro.data.model.initfavorite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitFavorite {
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("comment")
    @Expose
    private String comment;

    public InitFavorite() {
    }

    public InitFavorite(String user, String product, String comment) {
        this.user = user;
        this.product = product;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
