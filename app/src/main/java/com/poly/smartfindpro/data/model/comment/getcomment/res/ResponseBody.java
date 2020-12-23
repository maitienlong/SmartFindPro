
package com.poly.smartfindpro.data.model.comment.getcomment.res;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBody {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("comments")
    @Expose
    private List<Comments> comments = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

}
