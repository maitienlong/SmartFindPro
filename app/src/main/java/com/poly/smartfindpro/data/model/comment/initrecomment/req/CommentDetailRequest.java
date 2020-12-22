package com.poly.smartfindpro.data.model.comment.initrecomment.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentDetailRequest {
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("comment")
    @Expose
    private String oldComment;

    public CommentDetailRequest() {
    }

    public CommentDetailRequest(String user, String oldComment) {
        this.user = user;
        this.oldComment = oldComment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOldComment() {
        return oldComment;
    }

    public void setOldComment(String oldComment) {
        this.oldComment = oldComment;
    }
}
