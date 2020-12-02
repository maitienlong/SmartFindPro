package com.poly.smartfindpro.ui.user.profile;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentProfileBinding;
import com.poly.smartfindpro.ui.post.adapter.UtilitiesAdapter;
import com.poly.smartfindpro.ui.post.utilitiesPost.UtilitiesPresenter;
import com.poly.smartfindpro.ui.user.adapter.ProfileAdapter;
import com.poly.smartfindpro.ui.user.setting.SettingContact;
import com.poly.smartfindpro.ui.user.setting.SettingPresenter;
import com.poly.smartfindpro.ui.user.setting.information.InforFragment;

public class ProfileFragment extends BaseDataBindFragment<FragmentProfileBinding,ProfilePresenter> implements ProfileContact.ViewModel {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initView() {
        mPresenter = new ProfilePresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Trang cá nhân");
    }

    @Override
    protected void initData() {
        mPresenter = new ProfilePresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);


        ProfileAdapter utilitiesAdapter = new ProfileAdapter(mActivity, this);


        GridLayoutManager linearLayoutManager = new GridLayoutManager(mActivity, 1);
//        mBinding.rcProfile.setNestedScrollingEnabled(false);
        mBinding.rcProfile.setAdapter(utilitiesAdapter);
        mBinding.rcProfile.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }

    @Override
    public void onClickEditUser() {
        getBaseActivity().goToFragment(R.id.fl_user,new InforFragment(),null);
    }
}
