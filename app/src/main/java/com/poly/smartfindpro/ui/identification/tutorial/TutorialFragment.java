package com.poly.smartfindpro.ui.identification.tutorial;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentTutorialIdentifitionBinding;
import com.poly.smartfindpro.ui.identification.step.StepFragment;


public class TutorialFragment extends BaseDataBindFragment<FragmentTutorialIdentifitionBinding, TutorialPresenter> implements TutorialContract.ViewModel {


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
        return R.layout.fragment_tutorial_identifition;
    }

    @Override
    protected void initView() {

        mPresenter = new TutorialPresenter(mActivity, this);

        mBinding.setPresenter(mPresenter);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClickConfirm() {
        getBaseActivity().goToFragment(R.id.fl_identification, new StepFragment(), null);
    }
}
