package com.poly.smartfindpro.ui.post.model;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;

public class ImageInforPost {
    private String nameFile;

    private String realPath;

    private Bitmap bitmap;

    public ImageInforPost() {
    }

    public ImageInforPost(String nameFile, String realPath, Bitmap bitmap) {
        this.realPath = realPath;;
        this.nameFile = nameFile;
        this.bitmap = bitmap;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
