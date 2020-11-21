package com.poly.smartfindpro.ui.post.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;

public class ImageInforPost {
    public String mName;
    public Uri uri;

    public ImageInforPost(String mName, Uri uri) {
        this.mName = mName;
        this.uri = uri;
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
}
