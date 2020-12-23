package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.os.Build;
import android.util.Size;

import androidx.annotation.RequiresApi;

import java.util.Comparator;

/* compiled from: CameraView */
class CompareSizesByArea implements Comparator<Size> {
    CompareSizesByArea() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int compare(Size lhs, Size rhs) {
        return Long.signum((((long) lhs.getWidth()) * ((long) lhs.getHeight())) - (((long) rhs.getWidth()) * ((long) rhs.getHeight())));
    }
}
