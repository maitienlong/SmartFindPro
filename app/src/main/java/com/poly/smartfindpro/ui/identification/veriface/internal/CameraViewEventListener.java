package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.graphics.Bitmap;
import android.media.Image;

public interface CameraViewEventListener {
    void onBufferReady(Image image);

    void onCameraError(String str);

    void onCapturedImage(Bitmap bitmap);

    void onPreviewReady();
}
