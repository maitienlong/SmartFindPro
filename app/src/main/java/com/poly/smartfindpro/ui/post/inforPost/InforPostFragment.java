package com.poly.smartfindpro.ui.post.inforPost;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentInforPostBinding;
import com.poly.smartfindpro.ui.post.adapter.ImageInforPostAdapter;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;
import com.poly.smartfindpro.ui.post.model.ImageInforPost;
import com.poly.smartfindpro.ui.post.model.Information;
import com.poly.smartfindpro.ui.post.model.PostRequest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class InforPostFragment extends BaseDataBindFragment<FragmentInforPostBinding, InforPostPresenter>
        implements InforPostContract.ViewModel, View.OnTouchListener, View.OnClickListener {

    private static final int IMAGE_CODE = 1;
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

    private ArrayList<ImageInforPost> imageList;
    private ArrayList<String> imageListString;
    private ImageInforPostAdapter imagePostAdapter;


    InforPostPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_infor_post;
    }


    @Override
    protected void initView() {
        presenter = new InforPostPresenter(getContext(), this);

        imageList = new ArrayList<>();
        imageListString = new ArrayList<>();

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
        mBinding.rvImages.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mBinding.rvImages.setHasFixedSize(true);

    }

    //lay du lieu cua anh de hien thi
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int totalItem = data.getClipData().getItemCount();
                for (int i = 0; i < totalItem; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    String imageName = getFileName(imageUri);
                    try {
                        ImageInforPost item = new ImageInforPost(imageName, imageUri, MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), imageUri));

                        imageList.add(item);
                        imageListString.add(String.valueOf(item.getBitmap()));
                        Log.d("checkImageString", "onActivityResult: " + String.valueOf(item.getBitmap()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imagePostAdapter = new ImageInforPostAdapter(mActivity, imageList);
                    mBinding.rvImages.setAdapter(imagePostAdapter);
                }
            } else if (data.getData() != null) {
                Log.e("TAG", "onActivityResult: " + data.getData());
                Uri imageUri = data.getData();
                String imageName = getFileName(imageUri);

                try {
                    ImageInforPost item = new ImageInforPost(imageName, imageUri, MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), imageUri));
                    imageList.add(item);
                    imageListString.add(String.valueOf(item.getBitmap()));
                    Log.d("checkImageString", "onActivityResult: " + String.valueOf(item.getBitmap()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imagePostAdapter = new ImageInforPostAdapter(mActivity, imageList);
                mBinding.rvImages.setAdapter(imagePostAdapter);

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

//        information.setImageInforPost(imageList);
        information.setImage(imageListString);

        postRequest.setCategory(category);
        postRequest.setInformation(information);
        Log.d("checkListImage", String.valueOf(imageListString));
        onNext(new Gson().toJson(postRequest));

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
            presenter.handleData(category, mAmountPeople, mPrice, mDeposit, mGender, mElectricityBill, mWaterBill, mDescription);
        } else if (view.getId() == R.id.imgAddImages) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(intent, IMAGE_CODE);

        }
    }

    public void onNext(String jsonData) {
        Log.d("CheckLog", jsonData);
        Fragment fragment = new AddressPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Config.POST_BUNDEL_RES, jsonData);
        FragmentTransaction fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fl_post, fragment);
        fragmentTransaction.addToBackStack("addresspost");
        fragmentTransaction.commit();
    }


}
