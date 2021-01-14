package com.poly.smartfindpro.ui.intro;


import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.ConfigSharedPreferences;
import com.poly.smartfindpro.databinding.ActivityIntroBinding;
import com.poly.smartfindpro.ui.MainActivity;
import com.poly.smartfindpro.ui.detailpost.DetailPostActivity;
import com.poly.smartfindpro.ui.login.LoginActivity;

import java.security.MessageDigest;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class IntroActivity extends BaseDataBindActivity<ActivityIntroBinding,
        IntroPresenter> implements IntroContract.ViewModel {

    private View mDecorView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_intro;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        onHask();
        getHeightStatusBar();
        dynamicLink();
    }

    private void getHeightStatusBar() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            Config.HEIGHT_STATUS_BAR = getResources().getDimensionPixelSize(resourceId);
        }
    }

    private void dynamicLink() {
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
            @Override
            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                Uri deepLink = null;

                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.getLink();
                    Log.d("DynamicLinksss", deepLink.toString());
                }

                if (deepLink != null) {
                    Log.d("DynamicLinksss", deepLink.toString());

                    String currentPage = deepLink.getQueryParameter("id");

                    Log.d("DynamicLinksss", currentPage);

                    mPresenter.requestData(currentPage);
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("DynamicLinksss", "Deo thay dau ca");
            }
        });
    }


    @Override
    protected void initData() {
        mPresenter = new IntroPresenter(this, this);
        mBinding.setPresenter(mPresenter);
        checkSaveLogin();
    }

    private void onHask() {
        try {

            PackageInfo info = getPackageManager().getPackageInfo(

                    "com.poly.smartfindpro",

                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {

                MessageDigest md = MessageDigest.getInstance("SHA");

                md.update(signature.toByteArray());

                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }

        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    public void onNextLogin() {
        Animation animation = new AnimationUtils().loadAnimation(this, R.anim.slide_up);
        animation.setFillAfter(true);
        mBinding.imgLogo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.new_fade_in, R.anim.new_fade_out);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void onNextHome() {
        Animation animation = new AnimationUtils().loadAnimation(this, R.anim.slide_up);
        animation.setFillAfter(true);
        mBinding.imgLogo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.new_fade_in, R.anim.new_fade_out);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void onNextDetail(String data) {

        Animation animation = new AnimationUtils().loadAnimation(this, R.anim.slide_up);
        animation.setFillAfter(true);
        mBinding.imgLogo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Intent detailActivity = new Intent(IntroActivity.this, DetailPostActivity.class);

                detailActivity.putExtra(Config.POST_BUNDEL_RES, data);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(IntroActivity.this);

                stackBuilder.addNextIntentWithParentStack(detailActivity);

                PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void checkSaveLogin() {
        SharedPreferences prefs = getSharedPreferences(Config.NAME_FILE_PREFERENCE, Context.MODE_PRIVATE);
        mPresenter.getUpdateUser(prefs.getString(ConfigSharedPreferences.USERNAME, "root")
                , prefs.getString(ConfigSharedPreferences.PASSWORD, "root")
                , prefs.getBoolean(ConfigSharedPreferences.IS_SAVE, true));
    }

    public void onShowDialogMsg(String msg) {
        showAlertDialog(msg, "Thử lại", new AlertDialogListener() {
            @Override
            public void onAccept() {
                checkSaveLogin();
            }

            @Override
            public void onCancel() {
                checkSaveLogin();
            }
        });
    }

    @Override
    public void onAccountNotAvail(String msg) {
        showAlertDialog("Thông báo", msg, "Đăng nhập", "Trang chủ", false, new AlertDialogListener() {
            @Override
            public void onAccept() {
                onNextLogin();
            }

            @Override
            public void onCancel() {
                onNextHome();
            }
        });
    }
}