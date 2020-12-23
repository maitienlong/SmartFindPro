
package com.poly.smartfindpro.data.model.identification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestIndentifi {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("issuedBy")
    @Expose
    private String issuedBy;
    @SerializedName("expiryDate")
    @Expose
    private String expiryDate;
    @SerializedName("homeTown")
    @Expose
    private String homeTown;
    @SerializedName("resident")
    @Expose
    private String resident;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("previous")
    @Expose
    private String previous;
    @SerializedName("behind")
    @Expose
    private String behind;
    @SerializedName("hasFace")
    @Expose
    private String hasFace;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getResident() {
        return resident;
    }

    public void setResident(String resident) {
        this.resident = resident;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getBehind() {
        return behind;
    }

    public void setBehind(String behind) {
        this.behind = behind;
    }

    public String getHasFace() {
        return hasFace;
    }

    public void setHasFace(String hasFace) {
        this.hasFace = hasFace;
    }

}
