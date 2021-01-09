package com.poly.smartfindpro.ui.user.setting.permission;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentUserPermissionBinding;
import com.poly.smartfindpro.ui.user.setting.information.InforPresenter;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PermissionFragment extends BaseDataBindFragment<FragmentUserPermissionBinding, PermissionPresenter>
        implements PermissionContact.ViewModel {

    private LoginButton loginButton;

    private CallbackManager callbackManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_permission;
    }

    @Override
    protected void initView() {
        mPresenter = new PermissionPresenter(mActivity, this);
        mBinding.setPresenter(mPresenter);
        mBinding.cmtb.setTitle("Quyền truy cập");


        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        loginButton = mBinding.btnLoginFacebook;

        loginButton.setPermissions("email");

        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showMessage("Bạn đã đăng nhập Facebook thành công");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                showMessage("Đăng nhập Facebook không thành công");

            }
        });


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackClick() {
        getBaseActivity().onBackFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}