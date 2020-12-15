package com.poly.smartfindpro.ui.user.profile;

import android.util.Log;
import android.widget.Toast;

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
        BindingUtils.setAdapter(mBinding.rcProfile, profileAdapter, true);
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
    public void onCallback(int type, String idPost) {

        if (type == 0) {
            mPresenter.getDeleteProduct(idPost);
        }
    }
}
