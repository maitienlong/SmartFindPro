package com.poly.smartfindpro.ui.post.confirmPost;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentConfirmPostBinding;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.adapter.ShowImagePostAdapter;
import com.poly.smartfindpro.ui.post.model.InforModel;
import com.poly.smartfindpro.ui.post.model.PostRequest;
import com.poly.smartfindpro.ui.post.utilitiesPost.model.UtilitiesModel;
//import com.poly.smartfindpro.ui.post.model.Address;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConfirmPostFragment extends BaseDataBindFragment<FragmentConfirmPostBinding, ConfirmPostPresenter> implements ConfirmPostContract.ViewModel {
    private PostRequest postRequest;

    private ShowImagePostAdapter showImagePostAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_post;
    }

    private void reciveData() {
        Type type = new TypeToken<PostRequest>() {
        }.getType();

        postRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);

    }

    @Override
    protected void initData() {
        mPresenter = new ConfirmPostPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);

        showImagePostAdapter = new ShowImagePostAdapter(mActivity);

        onShowImage();

        mPresenter.setTheLoai(postRequest.getCategory());

        mPresenter.setSoLuong(postRequest.getInformation().getAmountPeople());

        mPresenter.setGia(postRequest.getInformation().getPrice());

        mPresenter.setDatCoc(postRequest.getInformation().getDeposit());

        mPresenter.setGioiTinh(postRequest.getInformation().getGender());

        mPresenter.setDiaChi(postRequest.getAddress().getDetailAddress() + ", " + postRequest.getAddress().getCommuneWardTown() + ", " + postRequest.getAddress().getDistrictsTowns() + ", " + postRequest.getAddress().getProvinceCity());

        mPresenter.setTienDien(postRequest.getInformation().getElectricBill());

        mPresenter.setTienNuoc(postRequest.getInformation().getElectricBill());

        mPresenter.setTienIch(postRequest.getUtilities());

        mPresenter.setMoTa(postRequest.getInformation().getDescribe());
    }

    @Override
    protected void initView() {
        reciveData();
    }

    public void onShowImage() {
        showImagePostAdapter.setItemView(postRequest.getInformation().getImageShow());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        mBinding.rcPhoto.setLayoutManager(linearLayoutManager);
        mBinding.rcPhoto.setAdapter(showImagePostAdapter);

    }


}

