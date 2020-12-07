
package com.poly.smartfindpro.data.model.post.res.postresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostResponse {

    @SerializedName("response_header")
    @Expose
    private PostResponseHeader postResponseHeader;
    @SerializedName("response_body")
    @Expose
    private Object responseBody;

    public PostResponseHeader getPostResponseHeader() {
        return postResponseHeader;
    }

    public void setPostResponseHeader(PostResponseHeader postResponseHeader) {
        this.postResponseHeader = postResponseHeader;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

}
