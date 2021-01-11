package com.poly.smartfindpro.ui.identification.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.ActivityIdentifycationBinding;
import com.poly.smartfindpro.ui.identification.tutorial.TutorialFragment;


public class IdentificationActivity extends BaseDataBindActivity<ActivityIdentifycationBinding, IdentificationPresenter> implements IdentificationContract.ViewModel {
    private int REQUEST_CAMERA_PERMISSION = 5;

    @Override
    protected int getLayoutId() {
        Config.setStatusBarGradiant(this);
        return R.layout.activity_identifycation;
    }

    @Override
    protected void initView() {

        initPermission();
    }

    @Override
    protected void initData() {

    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                //
                goToFragment(R.id.fl_identification, new TutorialFragment(), new Bundle());
            }
        } else {
            //
            goToFragment(R.id.fl_identification, new TutorialFragment(), new Bundle());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goToFragment(R.id.fl_identification, new TutorialFragment(), new Bundle());
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
                        startActivityForResult(intent, REQUEST_CAMERA_PERMISSION);
                    }

                    @Override
                    public void onCancel() {
                        finish();
                    }
                });
    }
}
