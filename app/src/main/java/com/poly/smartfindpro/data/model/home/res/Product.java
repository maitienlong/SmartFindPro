
package com.poly.smartfindpro.data.model.home.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.poly.smartfindpro.data.model.base.User;

public class Product {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("product")
    @Expose
    private Product_ product;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createAt")
    @Expose

    private String createAt;

    public String getTotal_people_lease() {
        return total_people_lease;
    }

    public void setTotal_people_lease(String total_people_lease) {
        this.total_people_lease = total_people_lease;
    }

    @SerializedName("total_people_lease")
    @Expose
    private String total_people_lease;
    @SerializedName("updateAt")
    @Expose
    private String updateAt;
    @SerializedName("deleteAt")
    @Expose
    private String deleteAt;
    @SerializedName("linkProduct")
    @Expose
    private String linkProduct;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product_ getProduct() {
        return product;
    }

    public void setProduct(Product_ product) {
        this.product = product;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(String deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getLinkProduct() {
        return linkProduct;
    }

    public void setLinkProduct(String linkProduct) {
        this.linkProduct = linkProduct;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
