package com.poly.smartfindpro.ui.detailpost;

import java.util.ArrayList;

public class Product {
    private  int  id;
    private  String  avatar;
    private  String  name;
    private  String  date;
    private  String  type;
    private  String  sex;
    private  String  amount;
    private  String  content;
    private  String  price;
    private  String  address;
    private  ArrayList<String>  imageArrayList;

    public Product() {
    }

    public Product(int id, String avatar, String name, String date, String type, String sex, String amount, String content, String price, String address, ArrayList<String> imageArrayList) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.date = date;
        this.type = type;
        this.sex = sex;
        this.amount = amount;
        this.content = content;
        this.price = price;
        this.address = address;
        this.imageArrayList = imageArrayList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getImageArrayList() {
        return imageArrayList;
    }

    public void setImageArrayList(ArrayList<String> imageArrayList) {
        this.imageArrayList = imageArrayList;
    }
}
