package com.poly.smartfindpro.ui;


import android.graphics.ColorFilter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityMainBinding;
import com.poly.smartfindpro.ui.home.HomeFragment;
import com.poly.smartfindpro.ui.message.ListUserChatFragment;
import com.poly.smartfindpro.ui.searchProduct.SearchProductActivity;
import com.poly.smartfindpro.ui.user.userFragment.UserFragment;


public class MainActivity extends BaseDataBindActivity<ActivityMainBinding,
        MainPresenter> implements MainContract.ViewModel {

    private ColorFilter oldColors;

    private int position = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        mPresenter = new MainPresenter(this, this);
        mBinding.setPresenter(mPresenter);

        setFragmentDef();


    }

    @Override
    protected void initData() {


    }


    private void setBottomNaviChange(int positon) {
        switch (positon) {
            case 0:
                mBinding.btnHome.setImageResource(R.drawable.ic__home_page_full);
                mBinding.btnMessage.setImageResource(R.drawable.ic_outline_message);
                mBinding.btnUser.setImageResource(R.drawable.ic_person_outline);
                break;
            case 1:
                mBinding.btnHome.setImageResource(R.drawable.ic_outline_home);
                mBinding.btnMessage.setImageResource(R.drawable.ic_message_full);
                mBinding.btnUser.setImageResource(R.drawable.ic_person_outline);
                break;
            case 2:
                mBinding.btnHome.setImageResource(R.drawable.ic_outline_home);
                mBinding.btnMessage.setImageResource(R.drawable.ic_outline_message);
                mBinding.btnUser.setImageResource(R.drawable.ic_person_full);
                break;
        }
    }


    @Override
    public void onSelectHome() {
        setFragmentDef();
    }

    @Override
    public void onSelecFind() {
        openActivity(SearchProductActivity.class);
    }

    @Override
    public void onSelectMessager() {
        if (position < 2) {
            goToFragmentReplaceLeft(R.id.fl_native, new ListUserChatFragment(), null);
        } else if (position > 2) {
            goToFragmentReplaceRight(R.id.fl_native, new ListUserChatFragment(), null);

        }
        position = 2;
        checkAnimation(position);
        setBottomNaviChange(1);
    }

    @Override
    public void onSelectUser() {
        if (position < 3) {
            goToFragmentReplaceLeft(R.id.fl_native, new UserFragment(), null);
        } else if (position > 3) {
            goToFragmentReplaceRight(R.id.fl_native, new UserFragment(), null);
        }
        position = 3;
        checkAnimation(position);
        setBottomNaviChange(2);
    }

    private void setFragmentDef() {
        if (position < 1) {
            goToFragmentReplaceLeft(R.id.fl_native, new HomeFragment(), null);
        } else if (position > 1) {
            goToFragmentReplaceRight(R.id.fl_native, new HomeFragment(), null);
        }
        position = 1;
        checkAnimation(position);
        setBottomNaviChange(0);
    }

    public void scaleViewSelect(View v, float endScale) {
        Animation anim = new ScaleAnimation(
                1f, endScale, // Start and end values for the X axis scaling
                1f, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true);
        anim.setDuration(600);
        v.startAnimation(anim);
    }

    public void scaleViewUnSelect(View v, float endScale) {
        Animation anim = new ScaleAnimation(
                1f, endScale, // Start and end values for the X axis scaling
                1f, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true);
        anim.setDuration(200);
        v.startAnimation(anim);
    }

    private void checkAnimation(int position) {
        switch (position) {
            case 1:
                scaleViewSelect(mBinding.btnHome, 1.2f);
                scaleViewSelect(mBinding.btnMessage, 1f);
                scaleViewSelect(mBinding.btnUser, 1f);

                break;
            case 2:
                scaleViewSelect(mBinding.btnHome, 1f);
                scaleViewSelect(mBinding.btnMessage, 1.2f);
                scaleViewSelect(mBinding.btnUser, 1f);
                break;
            case 3:
                scaleViewSelect(mBinding.btnHome, 1f);
                scaleViewSelect(mBinding.btnMessage, 1f);
                scaleViewSelect(mBinding.btnUser, 1.2f);
                break;
        }
    }
}