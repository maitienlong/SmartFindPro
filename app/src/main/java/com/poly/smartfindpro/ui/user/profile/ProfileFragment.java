package com.poly.smartfindpro.ui.user.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;

import com.google.gson.Gson;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.FragmentProfileBinding;
import com.poly.smartfindpro.ui.detailpost.DetailPostActivity;
import com.poly.smartfindpro.ui.login.otp.ConfirmOTPFragment;
import com.poly.smartfindpro.ui.post.PostActivity;
import com.poly.smartfindpro.ui.post.inforPost.InforPostFragment;
import com.poly.smartfindpro.ui.user.adapter.ProfileAdapter;
import com.poly.smartfindpro.ui.user.setting.information.InforFragment;
import com.poly.smartfindpro.utils.BindingUtils;

import java.util.List;

public class ProfileFragment extends BaseDataBindFragment<FragmentProfileBinding, ProfilePresenter> implements ProfileContact.ViewModel {

    ProfileAdapter profileAdapter;

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
//            Bundle bundle = new Bundle();
//            bundle.putString(Config.POST_BUNDlE_RES_ID, jsonData);
//            getBaseActivity().openActivity(PostActivity.class,bundle);
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
        mPresenter.getTotalPeopleLease(idPost,amount);
    }

    @Override
    public void setAmountPeople(String amount) {

    }
}
