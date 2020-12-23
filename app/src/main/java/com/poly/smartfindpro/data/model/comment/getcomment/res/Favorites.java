
package com.poly.smartfindpro.data.model.comment.getcomment.res;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorites {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("list")
    @Expose
    private List<Object> list = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

}
