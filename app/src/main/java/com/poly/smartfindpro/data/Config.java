package com.poly.smartfindpro.data;

import android.util.Log;

import com.poly.smartfindpro.data.model.base.User;
import com.poly.smartfindpro.data.model.login.req.LoginRequest;
import com.poly.smartfindpro.data.model.login.res.LoginResponse;
import com.poly.smartfindpro.data.model.login.res.ResponseBody;
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

    public static int getLogin(String username, String password) {
        isStatus = 0;

        LoginRequest request = new LoginRequest();

        request.setAccount(username);

        request.setPassword(password);

        MyRetrofitSmartFind.getInstanceSmartFind().getLogin(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.code() == 200) {
                    if (response.body().getResponseHeader().getResCode() == 200) {
                        String token = response.body().getResponseBody().getUser().getId();

                        int level = response.body().getResponseBody().getUser().getLevel();

                        PROFILE = response.body().getResponseBody().getUser();

                        TOKEN_USER = token;

                        LEVEL_ACCOUNT = level;

                        isStatus = 1;

                    } else {
                        TOKEN_USER = "root";

                        LEVEL_ACCOUNT = 0;

                        isStatus = 0;

                    }


                } else {
                    TOKEN_USER = "root";

                    LEVEL_ACCOUNT = 0;

                    isStatus = -1;

                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                TOKEN_USER = "root";

                LEVEL_ACCOUNT = 0;

                isStatus = -2;
            }
        });

        return isStatus;
    }

    public static boolean isClick(){
        if (TOKEN_USER != null && !TOKEN_USER.isEmpty()){
            return true;
        }
        return false;
    }

}
