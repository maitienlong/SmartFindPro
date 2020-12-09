package com.poly.smartfindpro.ui.login.createPassword;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.data.model.product.res.Products;
import com.poly.smartfindpro.data.model.register.regisRequest.RegisterRequest;
import com.poly.smartfindpro.databinding.FragmentCreatePasswordBinding;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.login.loginFragment.LoginFragment;

import java.lang.reflect.Type;

public class CreatePasswordFragment extends BaseDataBindFragment<FragmentCreatePasswordBinding, CreatePasswordPresenter> implements CreatePasswordContract.ViewModel {

    private RegisterRequest registerRequest;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_password;
    }


    private void getData() {
        Type type = new TypeToken<RegisterRequest>() {
        }.getType();

        registerRequest = new Gson().fromJson(getArguments().getString(Config.POST_BUNDEL_RES), type);
    }

    @Override
    protected void initView() {
        getData();
    }

    @Override
    protected void initData() {
        mPresenter = new CreatePasswordPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);

        mPresenter.setRegisterRequest(registerRequest);
    }

    public void OnBackClick() {
        getBaseActivity().onBackFragment();
    }

//    @Override
//    public void onClickRegister() {
//        mPresenter.onClickRegister();
//    }

    private void checkData() {
        if (mBinding.edtPassword.getText().toString() == mBinding.edtConfirmPassword.getText().toString()) {
            getBaseActivity().goToFragment(R.id.fl_native, new LoginFragment(), null);
        } else {
            showMessage("mat khau chua chinh xac");
        }
    }
}
