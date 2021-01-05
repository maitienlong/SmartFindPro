package com.poly.smartfindpro.data.model.product.totalPeopleLease;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalPeopleLeaseRequest {
    @SerializedName("total_people_lease")
    @Expose
    private String total_people_lease;

    public String getTotal_people_lease() {
        return total_people_lease;
    }

    public void setTotal_people_lease(String total_people_lease) {
        this.total_people_lease = total_people_lease;
    }


    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("userId")
    @Expose
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
