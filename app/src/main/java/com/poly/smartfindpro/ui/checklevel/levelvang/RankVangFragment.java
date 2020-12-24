package com.poly.smartfindpro.ui.checklevel.levelvang;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentViewLevel1Binding;
import com.poly.smartfindpro.databinding.FragmentViewLevel3Binding;


public class RankVangFragment extends BaseDataBindFragment<FragmentViewLevel3Binding, RankVangPresenter> implements RankVangContract.ViewModel {


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_level_3;
    }

    @Override
    protected void initView() {

        mPresenter = new RankVangPresenter(mActivity, this);

        mBinding.setPresenter(mPresenter);

    }

    @Override
    protected void initData() {

    }

}
