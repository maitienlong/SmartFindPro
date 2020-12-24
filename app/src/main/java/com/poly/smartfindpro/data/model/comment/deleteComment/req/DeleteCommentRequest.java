package com.poly.smartfindpro.data.model.comment.deleteComment.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteCommentRequest {
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("comment")
    @Expose
    private String comment;

    public DeleteCommentRequest() {
    }

    public DeleteCommentRequest(String user, String comment) {
        this.user = user;
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
