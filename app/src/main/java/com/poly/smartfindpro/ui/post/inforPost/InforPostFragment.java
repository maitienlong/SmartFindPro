package com.poly.smartfindpro.ui.post.inforPost;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentInforPostBinding;
import com.poly.smartfindpro.ui.post.adapter.ImageInforPostAdapter;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;
import com.poly.smartfindpro.data.model.post.req.ImageInforPost;
import com.poly.smartfindpro.data.model.post.req.Information;
import com.poly.smartfindpro.data.model.post.req.PostRequest;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class InforPostFragment extends BaseDataBindFragment<FragmentInforPostBinding, InforPostPresenter>
        implements InforPostContract.ViewModel, View.OnTouchListener, View.OnClickListener {
    private static final int IMAGE_PICK_CODE = 1000;

    private static final int MY_PERMISSIONS_REQUEST = 1001;
    String category;
    String mAmountPeople = "";
    String mPrice = "";
    String mDeposit = "";
    String mGender = "";
    String mElectricityBill = "";
    String mWaterBill = "";
    String mDescription = "";

    private PostRequest postRequest;
    private Information information;

    private List<ImageInforPost> imageListPath;

    private ImageInforPostAdapter imagePostAdapter;

    private String urlReal = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_infor_post;
    }


    @Override
    protected void initView() {

        imageListPath = new ArrayList<>();

        //chon the loai
        mBinding.btnNhaTro.setOnTouchListener(this);
        mBinding.btnOGhep.setOnTouchListener(this);
        mBinding.btnNguyenCan.setOnTouchListener(this);
        mBinding.btnChungCu.setOnTouchListener(this);
        mBinding.btnContinue.setOnClickListener(this);
        mBinding.imgAddImages.setOnClickListener(this);
    }


    @Override
    protected void initData() {

        mPresenter = new InforPostPresenter(getContext(), this);
        mBinding.setPresenter(mPresenter);

        mBinding.rvImages.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mBinding.rvImages.setHasFixedSize(true);

    }

    //lay du lieu cua anh de hien thi
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

                        imageListPath.add(item);

                    } catch (Exception e) {

                    }

                    // show image
                    onShowImage(imageListPath);

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

                    imageListPath.add(item);


                } catch (Exception e) {
                    Log.d("CheckLoge", e.toString());
                }

                // show image
                onShowImage(imageListPath);

            }
        } else {
            showMessage("Bạn chưa chọn ảnh nào");
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


    @Override
    public void onNextFragment() {

        postRequest = new PostRequest();
        information = new Information();

        information.setAmountPeople(Integer.valueOf(mAmountPeople));
        information.setPrice(Integer.valueOf(mPrice));
        information.setGender(mGender);
        information.setUnit("VNĐ");
        information.setDeposit(Integer.valueOf(mDeposit));
        information.setElectricBill(Integer.valueOf(mElectricityBill));
        information.setElectricUnit("Số");
        information.setWaterBill(Integer.valueOf(mWaterBill));
        information.setWaterUnit("Khối");
        information.setDescribe(mDescription);

        postRequest.setCategory(category);
        postRequest.setInformation(information);
        if (imageListPath != null && imageListPath.size() > 0) {
            onNext(new Gson().toJson(postRequest), new Gson().toJson(imageListPath));
        } else {
            showMessage("Bạn phải có ít nhất 1 ảnh");
        }


    }

    @Override
    public void onErrorCategory() {
        Toast.makeText(getContext(), R.string.text_category_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorInfor() {
        Toast.makeText(getContext(), R.string.text_infor_error_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorGender() {
        Toast.makeText(getContext(), R.string.text_gender_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btn_nha_tro:
                mBinding.btnNhaTro.setPressed(true);
                mBinding.btnChungCu.setPressed(false);
                mBinding.btnNguyenCan.setPressed(false);
                mBinding.btnOGhep.setPressed(false);
                category = mBinding.btnNhaTro.getText().toString();
                break;
            case R.id.btn_chung_cu:
                mBinding.btnNhaTro.setPressed(false);
                mBinding.btnChungCu.setPressed(true);
                mBinding.btnNguyenCan.setPressed(false);
                mBinding.btnOGhep.setPressed(false);
                category = mBinding.btnChungCu.getText().toString();
                break;

            case R.id.btn_nguyen_can:
                mBinding.btnNhaTro.setPressed(false);
                mBinding.btnChungCu.setPressed(false);
                mBinding.btnNguyenCan.setPressed(true);
                mBinding.btnOGhep.setPressed(false);
                category = mBinding.btnNguyenCan.getText().toString();
                break;

            case R.id.btn_o_ghep:
                mBinding.btnNhaTro.setPressed(false);
                mBinding.btnChungCu.setPressed(false);
                mBinding.btnNguyenCan.setPressed(false);
                mBinding.btnOGhep.setPressed(true);
                category = mBinding.btnOGhep.getText().toString();
                break;

        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnContinue) {
            mAmountPeople = mBinding.edtAmountPerson.getText().toString();
            mPrice = mBinding.edtPrice.getText().toString();
            mDeposit = mBinding.edtDeposit.getText().toString();

            //check The loai phong
            if (category == null) {
                onErrorCategory();
                return;
            }

            //gioi tinh
            if (mBinding.rbFemale.isChecked()) {
                mGender = mBinding.rbFemale.getText().toString();
            } else if (mBinding.rbMale.isChecked()) {
                mGender = mBinding.rbMale.getText().toString();
            } else if (mBinding.rbAll.isChecked()) {
                mGender = mBinding.rbAll.getText().toString();
            }
            mElectricityBill = mBinding.edtElectricityBill.getText().toString();
            mWaterBill = mBinding.edtWaterBill.getText().toString();
            mDescription = mBinding.edtDescription.getText().toString();
            mPresenter.handleData(category, mAmountPeople, mPrice, mDeposit, mGender, mElectricityBill, mWaterBill, mDescription);
        }
    }

    private void onShowImage(List<ImageInforPost> imageList) {
        imagePostAdapter = new ImageInforPostAdapter(mActivity, imageList);
        mBinding.rvImages.setAdapter(imagePostAdapter);
    }

    public void onNext(String jsonData, String jsonPhoto) {
        Log.d("CheckLog", jsonData);
        Log.d("CheckLogPhoto", jsonPhoto);

        Bundle bundle = new Bundle();

        bundle.putString(Config.POST_BUNDEL_RES, jsonData);

        bundle.putString(Config.POST_BUNDEL_RES_PHOTO, jsonPhoto);

        Intent intent = new Intent();

        intent.putExtra(Config.DATA_CALL_BACK, "2");

        intent.putExtra(Config.POST_BUNDEL_RES, jsonData);

        intent.putExtra(Config.POST_BUNDEL_RES_PHOTO, jsonPhoto);

        setResult(RESULT_OK, intent);

        onBackData();

        getBaseActivity().goToFragmentCallBackData(R.id.fl_post, new AddressPostFragment(), bundle, getOnFragmentDataCallBack());

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
}
