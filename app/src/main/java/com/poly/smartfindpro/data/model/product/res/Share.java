
package com.poly.smartfindpro.data.model.product.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Share {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("username")
    @Expose
    private String username;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
