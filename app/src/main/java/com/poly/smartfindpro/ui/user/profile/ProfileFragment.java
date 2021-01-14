package com.poly.smartfindpro.ui.user.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.post.req.ImageInforPost;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.FragmentProfileBinding;
import com.poly.smartfindpro.ui.detailpost.DetailPostActivity;
import com.poly.smartfindpro.ui.login.otp.ConfirmOTPFragment;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.ui.post.inforPost.RealPathUtil;
import com.poly.smartfindpro.ui.user.adapter.ProfileAdapter;
import com.poly.smartfindpro.ui.user.setting.information.InforFragment;
import com.poly.smartfindpro.utils.BindingUtils;

import java.util.List;

import static com.poly.smartfindpro.ui.post.inforPost.InforPostFragment.IMAGE_PICK_CODE;
import static com.poly.smartfindpro.ui.post.inforPost.InforPostFragment.MY_PERMISSIONS_REQUEST;

public class ProfileFragment extends BaseDataBindFragment<FragmentProfileBinding, ProfilePresenter> implements ProfileContact.ViewModel {

    ProfileAdapter profileAdapter;

    private int IMAGE_PICK_CODE_AVATAR = 123;
    private int IMAGE_PICK_CODE_COVER = 124;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initView() {
        mPresenter = new ProfilePresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Trang cá nhân");
//        mBinding.btnApproved.setBackgroundColor(R.drawable.btn_category_pressed);
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    @Override
    protected void initData() {
        mPresenter = new ProfilePresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);
        profileAdapter = new ProfileAdapter(mActivity, mActivity.getSupportFragmentManager(), this);

    }


    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }

    @Override
    public void onShow(List<Products> productList) {
        profileAdapter.setItemList(productList);
        BindingUtils.setAdapter(mBinding.rcProfile, profileAdapter, false);
    }


    @Override
    public void onClickEditUser() {
        getBaseActivity().goToFragment(R.id.fl_native, new InforFragment(), null);
    }

    @Override
    public void onClickPending() {
        mBinding.btnPending.setBackground(getResources().getDrawable(R.drawable.btn_category_pressed));
        mBinding.btnPending.setTextColor(getResources().getColor(R.color.white));
        mBinding.btnApproved.setBackground(getResources().getDrawable(R.drawable.btn_category));
        mBinding.btnApproved.setTextColor(getResources().getColor(R.color.blue));
    }

    @Override
    public void onClickApproved() {
//        mPresenter.onClickApproved();
        mBinding.btnPending.setBackground(getResources().getDrawable(R.drawable.btn_category));
        mBinding.btnPending.setTextColor(getResources().getColor(R.color.blue));
        mBinding.btnApproved.setBackground(getResources().getDrawable(R.drawable.btn_category_pressed));
        mBinding.btnApproved.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onCallback(int type, String idPost, String jsonData) {

        if (type == 0) {
            mPresenter.getDeleteProduct(idPost);

        } else {
            Intent intent = new Intent(mActivity, PostActivity.class);
            intent.putExtra(Config.POST_BUNDlE_RES_ID, new Gson().toJson(jsonData));
            mActivity.startActivity(intent);
        }
    }

    @Override
    public void onUpdate(String jsonData) {

    }

    @Override
    public void onGetTotalPeople(String idPost, String amount) {
        mPresenter.getTotalPeopleLease(idPost, amount);
    }

    @Override
    public void setAmountPeople(String amount) {

    }

    @Override
    public void onClickChangeAvatar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, MY_PERMISSIONS_REQUEST);
            } else {
                showImageGallery();
            }
        } else {
            showImageGallery();
        }
    }

    @Override
    public void onClickChangeCover() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, MY_PERMISSIONS_REQUEST);
            } else {
                showImageCover();
            }
        } else {
            showImageCover();
        }
    }

    @Override
    public void onShowPhoto() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE_AVATAR && resultCode == Activity.RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                showMessage("Bạn chỉ được lựa chọn 1 ảnh");
            } else if (data.getData() != null) {

                Uri imageUri = data.getData();
                //  Lay duong dan thuc te
                String realPath = RealPathUtil.getRealPath(mActivity, imageUri);

                mPresenter.initPhoto(0, realPath);
            }
        } else if (requestCode == IMAGE_PICK_CODE_COVER && resultCode == Activity.RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                showMessage("Bạn chỉ được lựa chọn 1 ảnh");
            } else if (data.getData() != null) {

                Uri imageUri = data.getData();
                //  Lay duong dan thuc te
                String realPath = RealPathUtil.getRealPath(mActivity, imageUri);

                mPresenter.initPhoto(1, realPath);
            }
        } else {
            showMessage("Bạn chưa chọn ảnh nào");
        }

    }

    private void showImageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE_AVATAR);
    }

    private void showImageCover() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE_COVER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showImageGallery();
                } else {
                    showMessage("Quyền truy cập đã được từ chối");
                }
        }
    }


}
