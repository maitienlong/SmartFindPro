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

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentInforPostBinding;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.adapter.ImageInforPostAdapter;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;
import com.poly.smartfindpro.ui.post.model.ImageInforPost;
import com.poly.smartfindpro.ui.post.model.Information;
import com.poly.smartfindpro.ui.post.model.PostRequest;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class InforPostFragment extends BaseDataBindFragment<FragmentInforPostBinding, InforPostPresenter>
        implements InforPostContract.ViewModel, View.OnTouchListener, View.OnClickListener {

    private static final int IMAGE_CODE = 1;

    private static final int GALLERY_KITKAT_INTENT_CALLED = 100;

    private static final int GALLERY_INTENT_CALLED = 101;

    private static final int MY_PERMISSIONS_REQUEST = 102;
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
        if (requestCode == GALLERY_KITKAT_INTENT_CALLED || requestCode == GALLERY_INTENT_CALLED && resultCode == Activity.RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int totalItem = data.getClipData().getItemCount();
                for (int i = 0; i < totalItem; i++) {
                    // URI
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();

                    // File name
                    String imageName = getFileName(imageUri);

                    //  Lay duong dan thuc te
                    String realPath = RealPathUtil.getRealPath(mActivity, imageUri);

                    mPresenter.onDemoUri(realPath);
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

                mPresenter.onDemoUri(realPath);
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
        Log.d("checkListImage", String.valueOf(imageListPath));
        onNext(new Gson().toJson(postRequest), new Gson().toJson(imageListPath));

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
        Log.d("CheckLog", jsonPhoto);

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
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, GALLERY_INTENT_CALLED);
        } else {
            showKitKatGallery();
        }
    }

    private void showKitKatGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
    }


    public void onShowPhoto() {
        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(mActivity, "Bạn cần phải cấp quền", Toast.LENGTH_SHORT).show();
            } else {

                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
            }
        } else {
            showImageGallery();
        }
    }


}
