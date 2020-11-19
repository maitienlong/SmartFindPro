
package com.poly.smartfindpro.ui.post.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Information {

    @SerializedName("amountPeople")
    @Expose
    private Integer amountPeople;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("deposit")
    @Expose
    private Integer deposit;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("electricBill")
    @Expose
    private Integer electricBill;
    @SerializedName("electricUnit")
    @Expose
    private String electricUnit;
    @SerializedName("waterBill")
    @Expose
    private Integer waterBill;
    @SerializedName("waterUnit")
    @Expose
    private String waterUnit;
    @SerializedName("describe")
    @Expose
    private String describe;

    @Override
    public String toString() {
        return "Information{" +
                "\n" +"amountPeople=" + amountPeople +
                "\n" +"price=" + price +
                "\n" +"deposit=" + deposit +
                "\n" +"gender='" + gender + '\'' +
                "\n" +"unit='" + unit + '\'' +
                "\n" +"electricBill=" + electricBill +
                "\n" +"electricUnit='" + electricUnit + '\'' +
                "\n" +"waterBill=" + waterBill +
                "\n" +"waterUnit='" + waterUnit + '\'' +
                "\n" +"describe='" + describe + '\'' +
                "\n" +"image=" + image +
                '}';
    }

    @SerializedName("image")
    @Expose


    private List<String> image = null;

    public Integer getAmountPeople() {
        return amountPeople;
    }

    public void setAmountPeople(Integer amountPeople) {
        this.amountPeople = amountPeople;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getElectricBill() {
        return electricBill;
    }

    public void setElectricBill(Integer electricBill) {
        this.electricBill = electricBill;
    }

    public String getElectricUnit() {
        return electricUnit;
    }

    public void setElectricUnit(String electricUnit) {
        this.electricUnit = electricUnit;
    }

    public Integer getWaterBill() {
        return waterBill;
    }

    public void setWaterBill(Integer waterBill) {
        this.waterBill = waterBill;
    }

    public String getWaterUnit() {
        return waterUnit;
    }

    public void setWaterUnit(String waterUnit) {
        this.waterUnit = waterUnit;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

}
