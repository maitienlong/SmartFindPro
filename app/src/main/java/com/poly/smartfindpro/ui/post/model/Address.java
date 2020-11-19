
package com.poly.smartfindpro.ui.post.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("provinceCity")
    @Expose
    private String provinceCity;
    @SerializedName("districtsTowns")
    @Expose
    private String districtsTowns;
    @SerializedName("communeWardTown")
    @Expose
    private String communeWardTown;
    @SerializedName("detailAddress")
    @Expose
    private String detailAddress;

    public String getProvinceCity() {
        return provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }

    public String getDistrictsTowns() {
        return districtsTowns;
    }

    public void setDistrictsTowns(String districtsTowns) {
        this.districtsTowns = districtsTowns;
    }

    public String getCommuneWardTown() {
        return communeWardTown;
    }

    public void setCommuneWardTown(String communeWardTown) {
        this.communeWardTown = communeWardTown;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

}
