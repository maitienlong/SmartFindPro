package com.poly.smartfindpro.ui.user.setting.confirmAccount;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentConfirmAccountBinding;
import com.poly.smartfindpro.ui.identification.activity.IdentificationActivity;
import com.poly.smartfindpro.ui.identification.step.StepFragment;

public class ConfirmAccountFragment extends BaseDataBindFragment<FragmentConfirmAccountBinding, ConfirmAccountPresenter>
        implements ConfirmAccountContact.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_account;
    }

    @Override
    protected void initView() {
        mPresenter = new ConfirmAccountPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
        mBinding.ctb.setTitle("Xác thực tài khoản");


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }

    @Override
    public void onConfirm() {
        getBaseActivity().openActivity(IdentificationActivity.class);
    }
}
