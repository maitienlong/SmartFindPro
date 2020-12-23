
package com.poly.smartfindpro.data.model.comment.initrecomment.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.poly.smartfindpro.data.model.comment.getcomment.res.Comments;

public class ResponseBody {

    @SerializedName("comment")
    @Expose
    private Comments comments;

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }
}
