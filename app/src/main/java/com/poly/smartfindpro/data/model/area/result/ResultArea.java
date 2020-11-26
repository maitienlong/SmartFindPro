
package com.poly.smartfindpro.data.model.area.result;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultArea {

    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;
    @SerializedName("listArea")
    @Expose
    private List<ListArea> listArea = null;

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
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

    public List<ListArea> getListArea() {
        return listArea;
    }

    public void setListArea(List<ListArea> listArea) {
        this.listArea = listArea;
    }

}
