package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.graphics.Bitmap;

public class TVSelfieImage {

    static final String PORTRAIT_LABEL = "portrait";

    static final String ID_LABEL = "portrait";

    private String label;

    private Bitmap image;


    public TVSelfieImage(String label, Bitmap image) {
        this.label = label;
        this.image = image;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Bitmap getImage() {
        return this.image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
