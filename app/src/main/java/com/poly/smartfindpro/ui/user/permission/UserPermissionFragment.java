package com.poly.smartfindpro.ui.user.permission;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.smartfindpro.R;
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment;
import com.poly.smartfindpro.databinding.FragmentUserPermissionBinding;

public class UserPermissionFragment extends BaseDataBindFragment<FragmentUserPermissionBinding, UserPermissionPresenter>
implements UserPermissionContact.ViewModel{

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_permission;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}