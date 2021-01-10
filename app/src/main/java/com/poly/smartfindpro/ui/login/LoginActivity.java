package com.poly.smartfindpro.ui.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.retrofit.MyRetrofit;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.ActivityLoginBinding;
import com.poly.smartfindpro.ui.login.loginFragment.LoginFragment;
import com.poly.smartfindpro.ui.login.registerFragment.RegisterFragment;


public class LoginActivity extends BaseDataBindActivity<ActivityLoginBinding, LoginPresenter> implements LoginContract.ViewModel {

    private boolean isLogin = true;

    private ViewTreeObserver observer;

    public ObservableField<Integer> currentHeight;

    public ObservableField<Integer> newHeight;

    @Override
    protected int getLayoutId() {
        Config.setStatusBarGradiant(this);
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        currentHeight = new ObservableField<>();
        currentHeight.set(mBinding.flLogin.getMeasuredHeight());

        newHeight = new ObservableField<>();

        observer = mBinding.flLogin.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                init();
            }
        });

        checkViewLogin();
        mBinding.btnChangeResign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = false;
                checkViewLogin();
            }
        });

        mBinding.btnChangeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLogin = true;
                checkViewLogin();
            }
        });


    }

    @Override
    protected void initData() {

    }

    private void checkViewLogin() {
        if (isLogin) {

            goToFragmentReplace(R.id.fl_Login, new LoginFragment(), null);
            mBinding.btnChangeLogin.setTextColor(getResources().getColor(R.color.background_login));
            mBinding.btnChangeResign.setTextColor(getResources().getColor(R.color.black));

        } else {


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.fl_Login, new RegisterFragment()).commit();

            mBinding.btnChangeResign.setTextColor(getResources().getColor(R.color.background_login));
            mBinding.btnChangeLogin.setTextColor(getResources().getColor(R.color.black));
            currentHeight.set(mBinding.cvLogin.getHeight());
        }
    }

    private void slideView(View view, int currentHeight, int newHeight) {

        ValueAnimator slideAnimator = ValueAnimator
                .ofInt(currentHeight, newHeight)
                .setDuration(200);

        /* We use an update listener which listens to each tick
         * and manually updates the height of the view  */

        slideAnimator.addUpdateListener(animation1 -> {
            Integer value = (Integer) animation1.getAnimatedValue();
            view.getLayoutParams().height = value.intValue();
            view.requestLayout();
        });

        /*  We use an animationSet to play the animation  */

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animationSet.play(slideAnimator);
        animationSet.start();
    }

    protected void init() {
        newHeight.set(mBinding.cvLogin.getHeight());
    }


}