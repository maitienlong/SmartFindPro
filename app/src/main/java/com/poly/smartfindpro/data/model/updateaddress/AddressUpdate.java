package com.poly.smartfindpro.data.model.updateaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressUpdate {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("communeWardTown")
    @Expose
    private String communeWardTown;
    @SerializedName("detailAddress")
    @Expose
    private String detailAddress;
    @SerializedName("districtsTowns")
    @Expose
    private String districtsTowns;
    @SerializedName("provinceCity")
    @Expose
    private String provinceCity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDistrictsTowns() {
        return districtsTowns;
    }

    public void setDistrictsTowns(String districtsTowns) {
        this.districtsTowns = districtsTowns;
    }

    public String getProvinceCity() {
        return provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }

}

