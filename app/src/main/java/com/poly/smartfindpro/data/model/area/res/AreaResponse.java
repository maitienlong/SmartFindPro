
package com.poly.smartfindpro.data.model.area.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreaResponse {

    @SerializedName("restHeader")
    @Expose
    private AreaResHeader areaResHeader;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("refId")
    @Expose
    private Object refId;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;
    @SerializedName("secure")
    @Expose
    private Boolean secure;

    public AreaResHeader getAreaResHeader() {
        return areaResHeader;
    }

    public void setAreaResHeader(AreaResHeader areaResHeader) {
        this.areaResHeader = areaResHeader;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Object getRefId() {
        return refId;
    }

    public void setRefId(Object refId) {
        this.refId = refId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

}
