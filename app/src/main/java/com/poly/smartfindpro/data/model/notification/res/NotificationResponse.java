package com.poly.smartfindpro.data.model.notification.res;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.poly.smartfindpro.data.model.base.ResponseHeader;

public class NotificationResponse {

    @SerializedName("response_header")
    @Expose
    private ResponseHeader responseHeader;
    @SerializedName("response_body")
    @Expose
    private NotificationBody responseBody;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public NotificationBody getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(NotificationBody responseBody) {
        this.responseBody = responseBody;
    }

}
