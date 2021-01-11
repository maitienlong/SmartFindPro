package com.poly.smartfindpro.data;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.data.model.base.User;
import com.poly.smartfindpro.data.model.login.req.LoginRequest;
import com.poly.smartfindpro.data.model.login.res.LoginResponse;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Config {
    public static String POST_BUNDEL_RES = "postReqBundel";

    public static String POST_BUNDEL_RES_PHOTO = "postReqBundelPhoto";

    public static String POST_BUNDEL_ID_OTP = "otpIdFirebase";

    public static String POST_BUNDEL_SDT = "sdtFirebase";

    public static String DATA_CALL_BACK = "dataCallBack";

    public static String POST_BUNDlE_RES_ID = "postReqBundleId";

    public static String TOKEN_USER = "";

    public static User PROFILE;

    public static int LEVEL_ACCOUNT = 0;

    public static String NAME_FILE_PREFERENCE = "smartFind";

    public static int RESULT_REQUEST = 1900;

    private static int isStatus;

    public static int HEIGHT_STATUS_BAR = 0;

    public static boolean isClick(){
        if (TOKEN_USER != null && !TOKEN_USER.isEmpty()){
            return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.background_hori);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }



}
