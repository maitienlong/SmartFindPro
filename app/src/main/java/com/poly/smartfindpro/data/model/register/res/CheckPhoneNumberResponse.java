
package com.poly.smartfindpro.data.model.register.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckPhoneNumberResponse {

    @SerializedName("response_header")
    @Expose
    private ResponseHeader responseHeader;
    @SerializedName("response_body")
    @Expose
    private Object responseBody;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

}
