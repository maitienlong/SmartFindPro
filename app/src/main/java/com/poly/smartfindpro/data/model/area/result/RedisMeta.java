
package com.poly.smartfindpro.data.model.area.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedisMeta {

    @SerializedName("timeMark")
    @Expose
    private Integer timeMark;

    public Integer getTimeMark() {
        return timeMark;
    }

    public void setTimeMark(Integer timeMark) {
        this.timeMark = timeMark;
    }

}
