package com.poly.smartfindpro.ui.post.model;

import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;

import java.util.List;

public class InforModel {
    private String category;
    private String numPerson;
    private String monthlyPrice;
    private String deposit;
    private String gender;
    private String address;
    private String electricBill;
    private String waterBill;
    private String description;

    public InforModel(String category, String numPerson, String monthlyPrice, String deposit, String gender, String address, String electricBill, String waterBill, String description) {
        this.category = category;
        this.numPerson = numPerson;
        this.monthlyPrice = monthlyPrice;
        this.deposit = deposit;
        this.gender = gender;
        this.address = address;
        this.electricBill = electricBill;
        this.waterBill = waterBill;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNumPerson() {
        return numPerson;
    }

    public void setNumPerson(String numPerson) {
        this.numPerson = numPerson;
    }

    public String getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(String monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getElectricBill() {
        return electricBill;
    }

    public void setElectricBill(String electricBill) {
        this.electricBill = electricBill;
    }

    public String getWaterBill() {
        return waterBill;
    }

    public void setWaterBill(String waterBill) {
        this.waterBill = waterBill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
