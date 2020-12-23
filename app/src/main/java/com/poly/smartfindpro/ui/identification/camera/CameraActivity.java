package com.poly.smartfindpro.ui.identification.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.databinding.ActivityIdentifycationBinding;
import com.poly.smartfindpro.databinding.ActivityTakePhotoBinding;
import com.poly.smartfindpro.ui.identification.tutorial.TutorialFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;


public class CameraActivity extends BaseDataBindActivity<ActivityTakePhotoBinding, CameraPresenter> implements CameraContract.ViewModel {

    private int REQUEST_CAMERA_PERMISSION = 2;

    private Camera mCamera;

    private CameraPreview mPreview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_take_photo;
    }

    @Override
    protected void initView() {
        initPermission();

    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                //
                onStartCamera();
            }
        } else {
            //
            onStartCamera();
        }
    }

    private void onStartCamera() {


    }


    @Override
    protected void initData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //
                    onStartCamera();
                }
            }
        }
    }


}
