package com.poly.smartfindpro.ui.intro;


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

import androidx.annotation.RequiresApi;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.ConfigSharedPreferences;
import com.poly.smartfindpro.databinding.ActivityIntroBinding;
import com.poly.smartfindpro.ui.MainActivity;
import com.poly.smartfindpro.ui.login.LoginActivity;

import java.security.MessageDigest;



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
    }


    @Override
    protected void initData() {
        mPresenter = new IntroPresenter(this, this);
        mBinding.setPresenter(mPresenter);
        checkSaveLogin();
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

    private void onHask(){
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("android.androidpractice", PackageManager.GET_SIGNATURES);
            for(Signature signature: packageInfo.signatures){
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT));
            }
        }catch (Exception e){
            Log.d("KeyHash", e.toString());
        }
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