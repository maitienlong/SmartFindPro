
package com.poly.smartfindpro.ui.post.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class    PostRequest {

    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("information")
    @Expose
    private Information information;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("utilities")
    @Expose
    private List<String> utilities = null;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("time")
    @Expose
    private String time;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getUtilities() {
        return utilities;
    }

    public void setUtilities(List<String> utilities) {
        this.utilities = utilities;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
