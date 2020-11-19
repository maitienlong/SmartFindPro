package com.poly.smartfindpro.ui.post.utilitiesPost.model;

public class UtilitiesModel {
private int image;
private String title;
private boolean status;

    public UtilitiesModel(int image, String title, boolean status) {
        this.image = image;
        this.title = title;
        this.status = status;
    }

    public UtilitiesModel() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
