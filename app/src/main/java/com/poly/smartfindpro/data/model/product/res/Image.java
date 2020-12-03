
package com.poly.smartfindpro.data.model.product.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("previous")
    @Expose
    private String previous;
    @SerializedName("behind")
    @Expose
    private String behind;

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getBehind() {
        return behind;
    }

    public void setBehind(String behind) {
        this.behind = behind;
    }

}
