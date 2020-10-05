package com.poly.smartfindpro.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class ActivityUtils {
    public static void showSoftInput(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void hideSoftInput(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager im = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (im != null) im.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
