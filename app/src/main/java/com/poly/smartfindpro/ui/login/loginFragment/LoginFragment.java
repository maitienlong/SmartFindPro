package com.poly.smartfindpro.ui.login.loginFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;


import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.callback.AlertDialogListener;
import com.poly.smartfindpro.data.Config;
import com.poly.smartfindpro.databinding.FragmentLoginBinding;
import com.poly.smartfindpro.ui.MainActivity;


public class LoginFragment extends BaseDataBindFragment<FragmentLoginBinding, LoginFragmentPresenter> implements LoginFragmentContract.ViewModel {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {
        getData();
        mPresenter = new LoginFragmentPresenter(mActivity, this, mBinding);
        mBinding.setPresenter(mPresenter);

    }

    private void getData() {
        if(getArguments() != null){
            mBinding.edtAccountNumber.setText(getArguments().getString(Config.POST_BUNDEL_RES));
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void saveLogin(String username, String password, String token) {

        getBaseActivity().showAlertDialog("Thông báo", "Bạn muốn ghi nhớ đăng nhập", "Có", "Không", true, new AlertDialogListener() {
            @Override
            public void onAccept() {
                showLoadingDialog();
                if (onSaveLogin(username, password, token)) {
                    Intent intent = new Intent(mActivity, MainActivity.class);
                    startActivity(intent);
                    mActivity.finish();
                } else {
                    showMessage("Lưu đăng nhập không thành công !");
                }

            }

            @Override
            public void onCancel() {
                showLoadingDialog();
                Intent intent = new Intent(mActivity, MainActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        });
//        }

    }

    @Override
    public void onShowDialog(String msg) {
        showAlertDialog("Thông báo", msg, "Đồng ý", "Từ chối", true, new AlertDialogListener() {
            @Override
            public void onAccept() {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public void onClickLogin() {
        if (mBinding.edtAccountNumber.getText().toString() == "" || mBinding.edtPassword.getText().toString() == "") {
            showMessage("vui long");
            Toast.makeText(mActivity, "vui long", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    private boolean onSaveLogin(String username, String password, String token) {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences("smartFind", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("token", token);
        editor.putBoolean("savestatus", true);

        return editor.commit();
    }
}
