
package com.poly.smartfindpro.data.model.post.req;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostRequest {
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
    @SerializedName("id")
    @Expose
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
