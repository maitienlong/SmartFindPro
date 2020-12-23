package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.graphics.Bitmap;

public interface LivenessDetectorListener {

    void onDetectionFailed(String var1);

    void onMovedToNextStep(FaceDetectionType var1, FaceDetectionType var2, Bitmap var3);

    void onDetectionSuccess(Bitmap var1);

    void onFaceDetected(Bitmap var1);

    void onResetStep();
}
