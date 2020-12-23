
package com.poly.smartfindpro.data.model.favorite;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.poly.smartfindpro.data.model.base.User;

public class ResponseBody {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("is_favorite")
    @Expose
    private Boolean isFavorite;
    @SerializedName("list_user")
    @Expose
    private List<User> listUser = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public List<User> getListUser() {
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }
}
