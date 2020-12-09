
package com.poly.smartfindpro.data.model.login.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseHeader {

    @SerializedName("res_name")
    @Expose
    private String resName;
    @SerializedName("res_code")
    @Expose
    private Integer resCode;
    @SerializedName("res_message")
    @Expose
    private String resMessage;

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public Integer getResCode() {
        return resCode;
    }

    public void setResCode(Integer resCode) {
        this.resCode = resCode;
    }

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }

}
