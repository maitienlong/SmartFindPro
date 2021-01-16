package com.poly.smartfindpro.ui;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.ActivityMainBinding;
import com.poly.smartfindpro.ui.checklevel.CheckLevelAccount;
import com.poly.smartfindpro.ui.home.HomeFragment;
import com.poly.smartfindpro.ui.notification.NotificationFragment;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.searchProduct.SearchProductActivity;
import com.poly.smartfindpro.ui.user.userFragment.UserFragment;


public class MainActivity extends BaseDataBindActivity<ActivityMainBinding,
        MainPresenter> implements MainContract.ViewModel {

    private int position = 0;
    private int MY_PERMISSIONS_REQUEST_LOCATION = 678;
    private int MY_PERMISSIONS_REQUEST_COARSE = 679;

    @Override
    protected int getLayoutId() {
        Config.setStatusBarGradiant(this);
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        mPresenter = new MainPresenter(this, this);
        mBinding.setPresenter(mPresenter);

        setFragmentDef();

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
    }

    @Override
    protected void initData() {


    }


    private void setBottomNaviChange(int positon) {
        switch (positon) {
            case 0:
                mBinding.btnHome.setImageResource(R.drawable.ic__home_page_full);
                mBinding.btnMessage.setImageResource(R.drawable.ic_notifications);
                mBinding.btnUser.setImageResource(R.drawable.ic_person_outline);
                break;
            case 1:
                mBinding.btnHome.setImageResource(R.drawable.ic_outline_home);
                mBinding.btnMessage.setImageResource(R.drawable.ic_baseline_notifications_24);
                mBinding.btnUser.setImageResource(R.drawable.ic_person_outline);
                break;
            case 2:
                mBinding.btnHome.setImageResource(R.drawable.ic_outline_home);
                mBinding.btnMessage.setImageResource(R.drawable.ic_notifications);
                mBinding.btnUser.setImageResource(R.drawable.ic_person_full);
                break;
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        Log.d("CheckStack", stackCount() + "");
        if (stackCount() > 3) {
            onBackFragment();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Nhấn một lần nữa để thoát ứng dụng", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }


    }

    @Override
    public void onSelectHome() {
        setFragmentDef();
    }

    @Override
    public void onSelecFind() {
        if (isNetworkConnected()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                    String[] fineLocation = {Manifest.permission.ACCESS_FINE_LOCATION};
                    String[] coarseLocation = {Manifest.permission.ACCESS_COARSE_LOCATION};
                    requestPermissions(fineLocation, MY_PERMISSIONS_REQUEST_LOCATION);
                    requestPermissions(coarseLocation, MY_PERMISSIONS_REQUEST_COARSE);
                } else {
                    openActivity(SearchProductActivity.class);
                }
            } else {
                openActivity(SearchProductActivity.class);
            }
        } else {
            showMessage("Vui lòng bật vị trí để tiếp tục");
        }
    }

    @Override
    public void onSelectMessager() {
        if (Config.LEVEL_ACCOUNT > 0) {
            if (position < 2) {
                goToFragmentReplaceLeft(R.id.fl_native, new NotificationFragment(), null);
            } else if (position > 2) {
                goToFragmentReplaceRight(R.id.fl_native, new NotificationFragment(), null);

            }
            position = 2;
            checkAnimation(position);
            setBottomNaviChange(1);
        } else {
            showMessage(getString(R.string.msg_đinhanh));
            CheckLevelAccount.onShowMessage(0);
        }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openActivity(SearchProductActivity.class);
                } else {
                    showSettingInfo();
                }
            }
        }
    }

    private void showSettingInfo() {
        showAlertDialog("Cảnh báo", getString(R.string.camera_open_setting),
                getString(R.string.contact_open_setting_text), getString(R.string.close), false, new AlertDialogListener() {
                    @Override
                    public void onAccept() {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, MY_PERMISSIONS_REQUEST_LOCATION);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    private boolean isNetworkConnected() {
        @SuppressLint("ServiceCast") LocationManager cm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        return cm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}