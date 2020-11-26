
package com.poly.smartfindpro.data.model.area.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreaRequest {

    @SerializedName("restHeader")
    @Expose
    private AreaReqHeader areaReqHeader;
    @SerializedName("body")
    @Expose
    private String body;

    public AreaReqHeader getAreaReqHeader() {
        return areaReqHeader;
    }

    public void setAreaReqHeader(AreaReqHeader areaReqHeader) {
        this.areaReqHeader = areaReqHeader;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
