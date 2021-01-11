
package com.poly.smartfindpro.data.model.notification.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationBody {

    @SerializedName("history")
    @Expose
    private List<Notification> notification;

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }

}
