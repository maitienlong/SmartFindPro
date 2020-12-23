
package com.poly.smartfindpro.data.model.comment.initcomment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitComment {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("oldComment")
    @Expose
    private String oldComment;
    @SerializedName("title")
    @Expose
    private String title;

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

    public String getOldComment() {
        return oldComment;
    }

    public void setOldComment(String oldComment) {
        this.oldComment = oldComment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
