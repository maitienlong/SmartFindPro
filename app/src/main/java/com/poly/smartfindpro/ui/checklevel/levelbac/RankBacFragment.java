package com.poly.smartfindpro.ui.checklevel.levelbac;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentViewLevel1Binding;
import com.poly.smartfindpro.databinding.FragmentViewLevel2Binding;


public class RankBacFragment extends BaseDataBindFragment<FragmentViewLevel2Binding, RankBacPresenter> implements RankBacContract.ViewModel {


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
        return R.layout.fragment_view_level_2;
    }

    @Override
    protected void initView() {

        mPresenter = new RankBacPresenter(mActivity, this);

        mBinding.setPresenter(mPresenter);

    }

    @Override
    protected void initData() {

    }

}
