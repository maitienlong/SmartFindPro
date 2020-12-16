
package com.poly.smartfindpro.data.model.area.req;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.poly.smartfindpro.data.model.base.Location;

public class AreaReqHeader {

    @SerializedName("channelCode")
    @Expose
    private String channelCode;
    @SerializedName("clientAddress")
    @Expose
    private String clientAddress;
    @SerializedName("clientRequestId")
    @Expose
    private String clientRequestId;
    @SerializedName("clientSessionId")
    @Expose
    private String clientSessionId;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("exchangeIV")
    @Expose
    private String exchangeIV;
    @SerializedName("systemCode")
    @Expose
    private String systemCode;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("platform")
    @Expose
    private String platform;
    @SerializedName("platformVersion")
    @Expose
    private String platformVersion;
    @SerializedName("sdkId")
    @Expose
    private String sdkId;
    @SerializedName("secretKey")
    @Expose
    private String secretKey;
    @SerializedName("signature")
    @Expose
    private String signature;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientRequestId() {
        return clientRequestId;
    }

    public void setClientRequestId(String clientRequestId) {
        this.clientRequestId = clientRequestId;
    }

    public String getClientSessionId() {
        return clientSessionId;
    }

    public void setClientSessionId(String clientSessionId) {
        this.clientSessionId = clientSessionId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getExchangeIV() {
        return exchangeIV;
    }

    public void setExchangeIV(String exchangeIV) {
        this.exchangeIV = exchangeIV;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getSdkId() {
        return sdkId;
    }

    public void setSdkId(String sdkId) {
        this.sdkId = sdkId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
