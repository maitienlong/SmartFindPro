package com.poly.smartfindpro.ui;


import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityMainBinding;
import com.poly.smartfindpro.ui.post.adapter.ViewPagerPostAdapter;


public class MainActivity extends BaseDataBindActivity<ActivityMainBinding,
        MainPresenter> implements MainContract.ViewModel {

    private ColorFilter oldColors;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        mPresenter = new MainPresenter(this, this);
        mBinding.setPresenter(mPresenter);

        ViewPagerPostAdapter viewPagerPostAdapter = new ViewPagerPostAdapter(getSupportFragmentManager(), this);
        mBinding.vpNative.setAdapter(viewPagerPostAdapter);

        setBottomNaviChange(0);

        mBinding.vpNative.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setBottomNaviChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initData() {


    }


    private void setBottomNaviChange(int positon){
        switch (positon){
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
        Log.d("CheckBackStack", getSupportFragmentManager().getBackStackEntryCount()+"");
        mBinding.vpNative.setCurrentItem(0);
        setBottomNaviChange(0);
    }

    @Override
    public void onSelecFind() {

    }

    @Override
    public void onSelectMessager() {
        mBinding.vpNative.setCurrentItem(1);
        setBottomNaviChange(1);
    }

    @Override
    public void onSelectUser() {
        mBinding.vpNative.setCurrentItem(2);
        setBottomNaviChange(2);
    }
}