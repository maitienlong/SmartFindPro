package com.poly.smartfindpro.ui.post.adressPost;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.login.loginFragment.LoginContract;
import com.poly.smartfindpro.ui.login.loginFragment.LoginPresenter;

public class AddressPostFragment extends BaseDataBindFragment<FragmentLoginBinding, LoginPresenter> implements AddressPostContract.ViewModel {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_address_post;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

}
