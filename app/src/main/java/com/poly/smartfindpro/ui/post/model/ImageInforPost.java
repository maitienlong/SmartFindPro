package com.poly.smartfindpro.ui.post.model;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;

public class ImageInforPost {
    public String mName;
    public Uri uri;

    private Bitmap bitmap;

    public ImageInforPost(String mName, Uri uri, Bitmap bitmap) {
        this.mName = mName;
        this.uri = uri;
        this.bitmap = bitmap;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public ImageInforPost(String mName) {
        this.mName = mName;
    }

    public ImageInforPost() {
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


}
