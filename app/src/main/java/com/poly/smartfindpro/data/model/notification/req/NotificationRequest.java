package com.poly.smartfindpro.data.model.notification.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationRequest {
    @SerializedName("user")
    @Expose
    private String user;
    public String getId() {
        return user;
    }

    public void setId(String user) {
        this.user = user;
    }

    public NotificationRequest(String user) {
        this.user = user;
    }
}
