
package com.poly.smartfindpro.data.model.product.deleteProduct.req.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteProductResponse {

    @SerializedName("response_body")
    @Expose
    private ResponseBody responseBody;
    @SerializedName("response_header")
    @Expose
    private ResponseHeader responseHeader;

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

}
