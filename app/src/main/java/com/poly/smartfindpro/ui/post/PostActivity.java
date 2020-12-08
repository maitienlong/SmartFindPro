package com.poly.smartfindpro.ui.post;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindActivity;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.callback.OnFragmentDataCallBack;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.ActivityPostBinding;
import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment;
import com.poly.smartfindpro.ui.post.anim.ProgressBarAnimation;
import com.poly.smartfindpro.ui.post.confirmPost.ConfirmPostFragment;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.data.model.post.req.ImageInforPost;
import com.poly.smartfindpro.data.model.post.req.PostRequest;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesPostFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PostActivity extends BaseDataBindActivity<ActivityPostBinding, PostPresenter> implements PostContract.ViewModel, OnFragmentDataCallBack {

    private PostRequest postRequest;

    private List<ImageInforPost> imagePost;

    private Type typeData;

    private Type typePhoto;

    private int position = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_post;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        mBinding.ctbPost.setTitle("Đăng bài");

        mBinding.pbTientrinh.setMax(100);

        mBinding.pbTientrinh.getIndeterminateDrawable().setTint(R.color.color_progress_loading);

        goToFragmentCallBackData(R.id.fl_post, new InforPostFragment(), null, this::onResult);

        statusProress("1");
    }

    @Override
    protected void initData() {
        mPresenter = new PostPresenter(this, this);

        mBinding.setPresenter(mPresenter);

        postRequest = new PostRequest();

        imagePost = new ArrayList<>();

        typeData = new TypeToken<PostRequest>() {
        }.getType();

        typePhoto = new TypeToken<List<ImageInforPost>>() {
        }.getType();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        Bundle bundle = new Bundle();
        bundle.putString(Config.POST_BUNDEL_RES, new Gson().toJson(postRequest));
        bundle.putString(Config.POST_BUNDEL_RES_PHOTO, new Gson().toJson(imagePost));

        switch (v.getId()) {
            case R.id.btn_infor:
                goToFragmentCallBackData(R.id.fl_post, new InforPostFragment(), bundle, this::onResult);
                statusProress("1");
                position = 1;
                break;

            case R.id.btn_address:
                goToFragmentCallBackData(R.id.fl_post, new AddressPostFragment(), bundle, this::onResult);
                statusProress("2");
                position = 2;
                break;

            case R.id.btn_tool:
                goToFragmentCallBackData(R.id.fl_post, new UtilitiesPostFragment(), bundle, this::onResult);
                statusProress("3");
                position = 3;
                break;

            case R.id.btn_confirm:

                break;
        }
    }


    public void statusProress(String isStatus) {
        switch (isStatus) {
            case "1":
                ProgressBarAnimation anim = new ProgressBarAnimation(mBinding.pbTientrinh, 0, 10);
                anim.setDuration(1000);
                mBinding.pbTientrinh.startAnimation(anim);
                mBinding.imgInfor.setImageResource(R.mipmap.btn_rdo_true);
                mBinding.imgAddress.setImageResource(R.mipmap.btn_rdo_false);
                mBinding.imgTool.setImageResource(R.mipmap.btn_rdo_false);
                mBinding.imgConfirm.setImageResource(R.mipmap.btn_rdo_false);

                break;

            case "2":
                ProgressBarAnimation anim2 = new ProgressBarAnimation(mBinding.pbTientrinh, 10, 50);
                anim2.setDuration(1000);
                mBinding.pbTientrinh.startAnimation(anim2);
                mBinding.imgInfor.setImageResource(R.mipmap.btn_rdo_true);
                mBinding.imgAddress.setImageResource(R.mipmap.btn_rdo_true);
                mBinding.imgTool.setImageResource(R.mipmap.btn_rdo_false);
                mBinding.imgConfirm.setImageResource(R.mipmap.btn_rdo_false);
                break;

            case "3":
                ProgressBarAnimation anim3 = new ProgressBarAnimation(mBinding.pbTientrinh, 50, 75);
                anim3.setDuration(1000);
                mBinding.pbTientrinh.startAnimation(anim3);
                mBinding.pbTientrinh.setProgress(75);
                mBinding.imgInfor.setImageResource(R.mipmap.btn_rdo_true);
                mBinding.imgAddress.setImageResource(R.mipmap.btn_rdo_true);
                mBinding.imgTool.setImageResource(R.mipmap.btn_rdo_true);
                mBinding.imgConfirm.setImageResource(R.mipmap.btn_rdo_false);
                break;
            case "4":
                ProgressBarAnimation anim4 = new ProgressBarAnimation(mBinding.pbTientrinh, 75, 100);
                anim4.setDuration(1000);
                mBinding.pbTientrinh.startAnimation(anim4);
                mBinding.pbTientrinh.setProgress(100);
                mBinding.imgInfor.setImageResource(R.mipmap.btn_rdo_true);
                mBinding.imgAddress.setImageResource(R.mipmap.btn_rdo_true);
                mBinding.imgTool.setImageResource(R.mipmap.btn_rdo_true);
                mBinding.imgConfirm.setImageResource(R.mipmap.btn_rdo_true);
                break;
            case "5":
                mBinding.linearLayout2.setVisibility(View.INVISIBLE);
                break;

        }


    }

    @Override
    public void onBackClick() {
        showAlertDialog("Thông báo", "Bạn muốn hủy bài đăng", "Đồng ý", "Hủy", true, new AlertDialogListener() {
            @Override
            public void onAccept() {
                finish();
            }

            @Override
            public void onCancel() {

            }
        });

    }


    @Override
    public void onResult(int resultCode, Intent intent) {
        Log.d("onResult", resultCode + "");
        if (resultCode == RESULT_OK && intent.hasExtra(Config.DATA_CALL_BACK)) {
            statusProress(intent.getStringExtra(Config.DATA_CALL_BACK));
            postRequest = new Gson().fromJson(intent.getStringExtra(Config.POST_BUNDEL_RES), typeData);
            imagePost = new Gson().fromJson(intent.getStringExtra(Config.POST_BUNDEL_RES_PHOTO), typePhoto);
        } else {

        }

    }


}
