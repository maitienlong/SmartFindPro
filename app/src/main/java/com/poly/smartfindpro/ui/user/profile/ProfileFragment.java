package com.poly.smartfindpro.ui.user.profile;

import android.util.Log;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.databinding.FragmentProfileBinding;
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
        mPresenter = new ProfilePresenter(mActivity, this,mBinding);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Trang cá nhân");
    }

    @Override
    protected void initData() {
        mPresenter = new ProfilePresenter(mActivity, this,mBinding);
        mBinding.setPresenter(mPresenter);
        profileAdapter = new ProfileAdapter(mActivity, mActivity.getSupportFragmentManager());

    }


    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }

    @Override
    public void onShow(List<Products> productList) {
        profileAdapter.setItemList(productList);
        BindingUtils.setAdapter(mBinding.rcProfile,profileAdapter,true);
    }


    @Override
    public void onClickEditUser() {
        getBaseActivity().goToFragment(R.id.fl_native, new InforFragment(), null);
    }
}
