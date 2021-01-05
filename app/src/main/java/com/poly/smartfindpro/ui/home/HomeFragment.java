package com.poly.smartfindpro.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.data.model.home.res.Product;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentHomeBinding;
import com.poly.smartfindpro.ui.home.adapter.HomeAdapter;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.adapter.MainSliderAdapter;
import com.poly.smartfindpro.ui.post.adapter.PicassoImageLoadingService;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.utils.BindingUtils;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

public class HomeFragment extends BaseDataBindFragment<FragmentHomeBinding, HomePresenter> implements HomeContract.ViewModel {
    private HomeAdapter homeAdapter;

    private static final int MY_PERMISSIONS_REQUEST = 1001;

    private List<String> mListImage;

    private MainSliderAdapter mainSliderAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mPresenter = new HomePresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);
        mListImage = new ArrayList<>();
        mListImage.add(MyRetrofitSmartFind.smartFind + "public/uploads/59df69e4e8bddc38a5af5f7337a481db.png");
        mListImage.add(MyRetrofitSmartFind.smartFind + "public/uploads/0cfdaa31a2c408d038b2cea7c8ca86a6.png");
        mListImage.add(MyRetrofitSmartFind.smartFind + "public/uploads/3242b6ad6e1e4966c9db0158161b2429.png");

        PicassoImageLoadingService picassoImageLoadingService = new PicassoImageLoadingService(mActivity);
        Slider.init(picassoImageLoadingService);
        mBinding.sliderHome.setAdapter(new MainSliderAdapter(mListImage));


    }

    @Override
    protected void initData() {
        mPresenter = new HomePresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);
        homeAdapter = new HomeAdapter(mActivity, mActivity.getSupportFragmentManager());


    }

    @Override
    public void onBackClick() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onCheckStatus(int status) {
        if (status == 1) {
            mBinding.btnShareRoom.setBackgroundTintList(mActivity.getColorStateList(R.color.white));
            mBinding.tvShareRoom.setTextColor(mActivity.getColorStateList(R.color.background));

            mBinding.btnRentalRoom.setBackgroundTintList(mActivity.getColorStateList(R.color.background));
            mBinding.tvRentalRoom.setTextColor(mActivity.getColorStateList(R.color.white));
        } else if (status == 2) {
            mBinding.btnRentalRoom.setBackgroundTintList(mActivity.getColorStateList(R.color.white));
            mBinding.tvRentalRoom.setTextColor(mActivity.getColorStateList(R.color.background));

            mBinding.btnShareRoom.setBackgroundTintList(mActivity.getColorStateList(R.color.background));
            mBinding.tvShareRoom.setTextColor(mActivity.getColorStateList(R.color.white));
        }
    }

    @Override
    public void showMessagePost(String msg) {
        showAlertDialog("Thông báo", msg, true, new AlertDialogListener() {
            @Override
            public void onAccept() {
                openPost();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public void onShow(List<Product> productList) {
        homeAdapter.setListItem(productList);
        BindingUtils.setAdapter(mBinding.rvList, homeAdapter, true);
    }


    @Override
    public void openPost() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, MY_PERMISSIONS_REQUEST);
            } else {
                getBaseActivity().openActivity(PostActivity.class);
            }
        } else {
            getBaseActivity().openActivity(PostActivity.class);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getBaseActivity().openActivity(PostActivity.class);
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
                        Uri uri = Uri.fromParts("package", getBaseActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, MY_PERMISSIONS_REQUEST);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }
}