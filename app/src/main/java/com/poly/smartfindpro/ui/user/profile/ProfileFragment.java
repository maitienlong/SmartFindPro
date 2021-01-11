package com.poly.smartfindpro.ui.user.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.post.req.ImageInforPost;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.retrofit.MyRetrofitSmartFind;
import com.poly.smartfindpro.databinding.FragmentProfileBinding;
import com.poly.smartfindpro.ui.detailpost.DetailPostActivity;
import com.poly.smartfindpro.ui.login.otp.ConfirmOTPFragment;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.adapter.ImageInforPostAdapter;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.ui.post.inforPost.RealPathUtil;
import com.poly.smartfindpro.ui.user.adapter.ProfileAdapter;
import com.poly.smartfindpro.ui.user.setting.information.InforFragment;
import com.poly.smartfindpro.utils.BindingUtils;

import java.util.List;

public class ProfileFragment extends BaseDataBindFragment<FragmentProfileBinding, ProfilePresenter> implements ProfileContact.ViewModel {

    ProfileAdapter profileAdapter;

    private static final int IMAGE_PICK_CODE = 1000;

    private static final int MY_PERMISSIONS_REQUEST = 1001;


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
        mBinding.btnPending.setBackground(getResources().getDrawable(R.drawable.btn_category));
        mBinding.btnPending.setTextColor(getResources().getColor(R.color.blue));
        mBinding.btnApproved.setBackground(getResources().getDrawable(R.drawable.btn_category_pressed));
        mBinding.btnApproved.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onCallback(int type, String idPost, String jsonData) {

        if (type == 0) {
            mPresenter.getDeleteProduct(idPost);

        }else {
            Intent intent = new Intent(mActivity, PostActivity.class);
            intent.putExtra(Config.POST_BUNDlE_RES_ID, new Gson().toJson(jsonData));
            mActivity.startActivity(intent);
        }
    }

    @Override
    public void onUpdate(String jsonData) {

    }


    private void showImageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    public void onShowPhoto() {
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int totalItem = data.getClipData().getItemCount();
                for (int i = 0; i < totalItem; i++) {
                    // URI
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();

                    // File name
                    String imageName = getFileName(imageUri);

                    //  Lay duong dan thuc te
                    String realPath = RealPathUtil.getRealPath(mActivity, imageUri);

                    //  mPresenter.onDemoUri(realPath);
                    // them du lieu vao object Image
                    try {
                        ImageInforPost item = new ImageInforPost(imageName, realPath, MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), imageUri));


                    } catch (Exception e) {

                    }

                    // show image
//                    onShowImage(imageListPath);

                }
            } else if (data.getData() != null) {

                Uri imageUri = data.getData();

                // File name
                String imageName = getFileName(imageUri);

                //  Lay duong dan thuc te
                String realPath = RealPathUtil.getRealPath(mActivity, imageUri);

                //  mPresenter.onDemoUri(realPath);
                // them du lieu vao object Image

                try {
                    ImageInforPost item = new ImageInforPost(imageName, realPath, MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), imageUri));

//                    imageListPath.add(item);

                } catch (Exception e) {
                    Log.d("CheckLoge", e.toString());
                }

//                // show image
//                onShowImage(imageListPath);

            }
        } else {
            showMessage("Bạn chưa chọn ảnh nào");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showImageGallery();
                } else {
                    showMessage("Quyền truy cập đã được từ chối");
                }
        }
    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = mActivity.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
            if (result == null) {
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
        }
        return result;

    }

}
