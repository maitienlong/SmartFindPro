package com.poly.smartfindpro.ui.checklevel.leveldong;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentTutorialIdentifitionBinding;
import com.poly.smartfindpro.databinding.FragmentViewLevel1Binding;
import com.poly.smartfindpro.ui.identification.step.StepFragment;


public class RankDongFragment extends BaseDataBindFragment<FragmentViewLevel1Binding, RankDongPresenter> implements RankDongContract.ViewModel {


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
        return R.layout.fragment_view_level_1;
    }

    @Override
    protected void initView() {

        mPresenter = new RankDongPresenter(mActivity, this);

        mBinding.setPresenter(mPresenter);

    }

    @Override
    protected void initData() {

    }

}
