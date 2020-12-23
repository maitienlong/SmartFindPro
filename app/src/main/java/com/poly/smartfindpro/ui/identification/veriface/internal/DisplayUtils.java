package com.poly.smartfindpro.ui.identification.veriface.internal;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class DisplayUtils {

    DisplayUtils() {
    }

    public static Point getScreenResolution(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        Display display = wm.getDefaultDisplay();
        Point screenResolution = new Point();
        display.getSize(screenResolution);

        return screenResolution;
    }

    public static int getScreenOrientation(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        Display display = wm.getDefaultDisplay();
        int orientation;
        if (display.getWidth() == display.getHeight()) {
            orientation = 3;
        } else if (display.getWidth() < display.getHeight()) {
            orientation = 1;
        } else {
            orientation = 2;
        }

        return orientation;
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }
}
