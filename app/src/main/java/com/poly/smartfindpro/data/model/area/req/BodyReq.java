package com.poly.smartfindpro.data.model.area.req;

public class BodyReq {
    private String areaType;
    private String parentCode;

    public BodyReq(String areaType, String parentCode) {
        this.areaType = areaType;
        this.parentCode = parentCode;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
