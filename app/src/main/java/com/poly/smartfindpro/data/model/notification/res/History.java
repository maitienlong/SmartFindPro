
package com.poly.smartfindpro.data.model.notification.res;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.poly.smartfindpro.data.model.product.res.Product;
import com.poly.smartfindpro.data.model.product.res.Products;

public class History {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("product")
    @Expose
    private Object product;
    @SerializedName("admin")
    @Expose
    private Object admin;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Object getAdmin() {
        return admin;
    }

    public void setAdmin(Object admin) {
        this.admin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
