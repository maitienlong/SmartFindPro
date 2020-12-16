package com.poly.smartfindpro.ui.intro;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.view.View;
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

    }


    @Override
    protected void initData() {
        mPresenter = new IntroPresenter(this, this);
        mBinding.setPresenter(mPresenter);
    }

    private void onNextLogin() {
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

    private void onNextHome() {
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

    private boolean checkSaveLogin() {
        SharedPreferences prefs = getSharedPreferences(Config.NAME_FILE_PREFERENCE, Context.MODE_PRIVATE);
        Config.TOKEN_USER = prefs.getString(ConfigSharedPreferences.TOKEN, "token");
        Config.LEVEL_ACCOUNT = prefs.getInt(ConfigSharedPreferences.LEVEL, 0);
        return prefs.getBoolean(ConfigSharedPreferences.IS_SAVE, false);
    }

    public void onShowDialogMsg(String msg) {
        showAlertDialog(msg, "Thử lại", new AlertDialogListener() {
            @Override
            public void onAccept() {
                mPresenter.getUpdateUser();
            }

            @Override
            public void onCancel() {
                mPresenter.getUpdateUser();
            }
        });
    }

    @Override
    public void onNextSceen() {
        if (!checkSaveLogin()) {
            onNextLogin();
        } else {
            onNextHome();
        }
    }
}