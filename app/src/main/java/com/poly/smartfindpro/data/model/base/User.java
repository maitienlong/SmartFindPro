
package com.poly.smartfindpro.data.model.base;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.poly.smartfindpro.data.model.home.res.Address;
import com.poly.smartfindpro.data.model.product.res.Share;

public class User {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("full_name")
    @Expose
    private String fullname;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("birth")
    @Expose
    private String birth;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("coverImage")
    @Expose
    private String coverImage;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("share")
    @Expose
    private List<Share> share = null;

    @SerializedName("level")
    @Expose
    private int level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Share> getShare() {
        return share;
    }

    public void setShare(List<Share> share) {
        this.share = share;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
