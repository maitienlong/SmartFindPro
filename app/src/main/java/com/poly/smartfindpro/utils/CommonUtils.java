package com.poly.smartfindpro.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class CommonUtils {
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            connectivity.getAllNetworkInfo();
        }
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        for (NetworkInfo anInfo : info) {
            if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static Locale getLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }

    public static String getTag(Class cls) {
        String className = cls.getSimpleName();

        if (className.length() > 23)
            return className.substring(0, 23);
        return className;
    }

    public static String fomatPriceVND(String money) {
        if (money == "0" || money.equals(""))
            return "";
        ArrayList<String> words = new ArrayList<String>();
        String f = money;
        StringTokenizer tokens = new StringTokenizer(f, ".");
        while (tokens.hasMoreTokens())
            words.add(tokens.nextToken());
        DecimalFormat fmt = new DecimalFormat();
        DecimalFormatSymbols fmts = new DecimalFormatSymbols();

        fmts.setGroupingSeparator(',');

        fmt.setGroupingSize(3);
        fmt.setGroupingUsed(true);
        fmt.setDecimalFormatSymbols(fmts);
        if (words.size() == 1) {
            BigDecimal bg1 = new BigDecimal(words.get(0));
            return fmt.format(bg1);
        }
        {
            BigDecimal bg1 = new BigDecimal(words.get(0));
            if (Integer.parseInt(words.get(1)) > 0)
                return fmt.format(bg1) + "." + words.get(1);
            else return fmt.format(bg1);
        }
    }

    public static String convertCountDownTime(int countDownTime) {
        int minute = countDownTime / (60 * 1000);
        int seconds = (countDownTime - minute * 60 * 1000) / 1000;
        if (seconds < 10) {
            return minute + ":0" + seconds;
        } else {
            return minute + ":" + seconds;
        }
    }

    public static Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0D;
        }
    }

    public static Long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return 0L;
        }
    }

    public static Integer parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    public static Float parseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            return 0f;
        }
    }
}
