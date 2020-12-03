
package com.poly.smartfindpro.data.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdentityCard {

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
    @SerializedName("homeTown")
    @Expose
    private HomeTown homeTown;
    @SerializedName("resident")
    @Expose
    private Resident resident;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("issuedBy")
    @Expose
    private String issuedBy;
    @SerializedName("expiryDate")
    @Expose
    private String expiryDate;
    @SerializedName("image")
    @Expose
    private Image image;

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

    public HomeTown getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(HomeTown homeTown) {
        this.homeTown = homeTown;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
