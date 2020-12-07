package com.poly.smartfindpro.ui.user.setting.confirmAccount;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentConfirmAccountBinding;

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
        mBinding.cmtb.setTitle("Xác thực tài khoản");
        Spinner spinner = mBinding.spnType;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mActivity,
                R.array.type_id_card_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }
}
