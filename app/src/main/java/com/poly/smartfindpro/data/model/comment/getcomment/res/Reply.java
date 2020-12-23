
package com.poly.smartfindpro.data.model.comment.getcomment.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reply {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("list")
    @Expose
    private List<Comments> listComment = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Comments> getList() {
        return listComment;
    }

    public void setList(List<Comments> list) {
        this.listComment = list;
    }

}
