package com.poly.smartfindpro.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mPresenter = new HomePresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);
        mListImage = new ArrayList<>();
        mListImage.add(MyRetrofitSmartFind.smartFind + "public/uploads/6689d038a175f89e7e2acdac07e39be2.png");
        mListImage.add(MyRetrofitSmartFind.smartFind + "public/uploads/afe901450f332ffcee0155dab866bab7.png");
        mListImage.add(MyRetrofitSmartFind.smartFind + "public/uploads/875170c056430db21853fdb746900bcc.png");
        mListImage.add(MyRetrofitSmartFind.smartFind + "public/uploads/c4192b690f75b2abea7a2c90f8a57d01.png");
        mListImage.add("https://cf.shopee.vn/file/b11156b9ac3994c99dcd87d69d601ce1");

        PicassoImageLoadingService picassoImageLoadingService = new PicassoImageLoadingService(mActivity);
        Slider.init(picassoImageLoadingService);
        mBinding.sliderHome.setAdapter(new MainSliderAdapter(mListImage));

        mBinding.srlHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onRefresh() {
                onCheckStatus(1);
                mPresenter.getProductRental();
            }
        });

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

            mBinding.btnRentalRoom.setBackground(getResources().getDrawable(R.drawable.btn_category_pressed));
            mBinding.btnRentalRoom.setTextColor(getResources().getColor(R.color.white));

            mBinding.btnShareRoom.setBackground(getResources().getDrawable(R.drawable.btn_category));
            mBinding.btnShareRoom.setTextColor(getResources().getColor(R.color.blue));
        } else if (status == 2) {
            mBinding.btnRentalRoom.setBackground(getResources().getDrawable(R.drawable.btn_category));
            mBinding.btnRentalRoom.setTextColor(getResources().getColor(R.color.blue));

            mBinding.btnShareRoom.setBackground(getResources().getDrawable(R.drawable.btn_category_pressed));
            mBinding.btnShareRoom.setTextColor(getResources().getColor(R.color.white));
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
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if(mBinding.srlHome.isRefreshing()){
                mBinding.srlHome.setRefreshing(false);
            }
            hideLoading();
            homeAdapter.setListItem(productList);
            mBinding.rvList.setHasFixedSize(true);
            BindingUtils.setAdapter(mBinding.rvList, homeAdapter, true);

        }, 150);

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