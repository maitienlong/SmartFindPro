
package com.poly.smartfindpro.data.model.area.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.poly.smartfindpro.data.model.area.Location;

public class AreaResHeader {

    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("channelId")
    @Expose
    private Integer channelId;
    @SerializedName("channelCode")
    @Expose
    private String channelCode;
    @SerializedName("systemCode")
    @Expose
    private String systemCode;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("sdkId")
    @Expose
    private String sdkId;
    @SerializedName("clientRequestId")
    @Expose
    private String clientRequestId;
    @SerializedName("clientSessionId")
    @Expose
    private String clientSessionId;
    @SerializedName("clientAddress")
    @Expose
    private String clientAddress;
    @SerializedName("platform")
    @Expose
    private String platform;
    @SerializedName("platformVersion")
    @Expose
    private String platformVersion;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("secretKey")
    @Expose
    private String secretKey;
    @SerializedName("exchangeIV")
    @Expose
    private String exchangeIV;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("partnerCode")
    @Expose
    private Object partnerCode;
    @SerializedName("sdkVersionNumber")
    @Expose
    private Object sdkVersionNumber;
    @SerializedName("sdkVersionString")
    @Expose
    private Object sdkVersionString;
    @SerializedName("token")
    @Expose
    private Object token;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSdkId() {
        return sdkId;
    }

    public void setSdkId(String sdkId) {
        this.sdkId = sdkId;
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

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getExchangeIV() {
        return exchangeIV;
    }

    public void setExchangeIV(String exchangeIV) {
        this.exchangeIV = exchangeIV;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Object getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(Object partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Object getSdkVersionNumber() {
        return sdkVersionNumber;
    }

    public void setSdkVersionNumber(Object sdkVersionNumber) {
        this.sdkVersionNumber = sdkVersionNumber;
    }

    public Object getSdkVersionString() {
        return sdkVersionString;
    }

    public void setSdkVersionString(Object sdkVersionString) {
        this.sdkVersionString = sdkVersionString;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

}
