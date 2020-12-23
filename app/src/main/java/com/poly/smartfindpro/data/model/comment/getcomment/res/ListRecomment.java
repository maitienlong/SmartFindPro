
package com.poly.smartfindpro.data.model.comment.getcomment.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.poly.smartfindpro.data.model.base.User;

public class ListRecomment {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("oldComment")
    @Expose
    private String oldComment;
    @SerializedName("updateAt")
    @Expose
    private String updateAt;
    @SerializedName("deleteAt")
    @Expose
    private String deleteAt;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOldComment() {
        return oldComment;
    }

    public void setOldComment(String oldComment) {
        this.oldComment = oldComment;
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
